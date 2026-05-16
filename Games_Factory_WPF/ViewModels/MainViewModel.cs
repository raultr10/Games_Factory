using Games_Factory.Services;
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

        public CartService CartService { get; }

        public RelayCommand NavigateHomeCommand { get; }
        public RelayCommand NavigateGamesCommand { get; }
        public RelayCommand NavigateNewsCommand { get; }
        public RelayCommand NavigateLoginCommand { get; }
        public RelayCommand LogoutCommand { get; }
        public RelayCommand NavigateCartCommand { get; }

        public MainViewModel()
        {
            // Inicializamos el servicio del carrito.
            CartService = new CartService();

            NavigateHomeCommand = new RelayCommand(o => CurrentView = new HomeViewModel(this));
            NavigateGamesCommand = new RelayCommand(o => CurrentView = new GamesViewModel(this));
            NavigateNewsCommand = new RelayCommand(o => CurrentView = new NewsViewModel());
            NavigateLoginCommand = new RelayCommand(o => CurrentView = new LoginViewModel(this));
            NavigateCartCommand = new RelayCommand(o => CurrentView = new CartViewModel(this));

            // Cierro la sesión actual del usuario.
            LogoutCommand = new RelayCommand(o =>
            {
                if (MessageBox.Show("¿Seguro que quieres cerrar sesión?", "Salir", MessageBoxButton.YesNo) == MessageBoxResult.Yes)
                {
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

                // Le pedimos al servicio que busque y desencripte el carrito del usuario indicado.
                CartService.LoadCartSecurely(App.CurrentUser.IdDni);
            }
            else
            {
                IsUserLoggedIn = false;
                UserName = "";

                // Limpiamos la lista del servicio para que no se quede guardado en la memoria.
                CartService.CartItems.Clear();
            }
        }
    }
}
