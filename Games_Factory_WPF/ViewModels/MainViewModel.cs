using Games_Factory.ViewModels.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace Games_Factory.ViewModels
{
    public class MainViewModel : ViewModelBase
    {
        private ViewModelBase _currentView;
        private string _globalSearch;

        private bool _isUserLoggedIn;
        private string _userName;

        public bool IsUserLoggedIn
        {
            get => _isUserLoggedIn;
            set => SetProperty(ref _isUserLoggedIn, value);
        }

        public string UserName
        {
            get => _userName;
            set => SetProperty(ref _userName, value);
        }

        public string GlobalSearch
        {
            get => _globalSearch;
            set
            {
                SetProperty(ref _globalSearch, value);
                if (CurrentView is ISearchable searchableViewModel)
                    searchableViewModel.SearchText = value;
            }
        }

        public ViewModelBase CurrentView
        {
            get => _currentView;
            set { SetProperty(ref _currentView, value); GlobalSearch = ""; }
        }

        public RelayCommand NavigateHomeCommand { get; }
        public RelayCommand NavigateGamesCommand { get; }
        public RelayCommand NavigateNewsCommand { get; }
        public RelayCommand NavigateLoginCommand { get; }

        public RelayCommand LogoutCommand { get; }

        public MainViewModel()
        {
            NavigateHomeCommand = new RelayCommand(o => CurrentView = new HomeViewModel(this));
            NavigateGamesCommand = new RelayCommand(o => CurrentView = new GamesViewModel(this));
            NavigateNewsCommand = new RelayCommand(o => CurrentView = new NewsViewModel());
            NavigateLoginCommand = new RelayCommand(o => CurrentView = new LoginViewModel(this));

            // Cierro la sesión actual del usuario.
            LogoutCommand = new RelayCommand(o =>
            {
                if (MessageBox.Show("¿Seguro que quieres cerrar sesión?", "Salir", MessageBoxButton.YesNo) == MessageBoxResult.Yes)
                {
                    // Elimino la referencia al usuario actual.
                    App.CurrentUser = null;

                    // Refresco el estado de la interfaz.
                    UpdateSessionStatus();

                    // Navego hasta la vista de inicio.
                    CurrentView = new HomeViewModel(this);
                    MessageBox.Show("Sesión cerrada correctamente.");
                }
            });

            CurrentView = new HomeViewModel(this);

            // Verifico el estado de la sesión al arrancar.
            UpdateSessionStatus();
        }

        // Actualizo la interfaz dependiendo si hay un usuario logueado.
        public void UpdateSessionStatus()
        {
            if (App.CurrentUser != null)
            {
                IsUserLoggedIn = true;
                UserName = App.CurrentUser.Nombre;
            }
            else
            {
                IsUserLoggedIn = false;
                UserName = "";
            }
        }
    }
}
