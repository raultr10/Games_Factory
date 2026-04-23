using Games_Factory.Models;
using Games_Factory.Services;
using Games_Factory.ViewModels.Base;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.ViewModels
{
    public class GamesViewModel : ViewModelBase, ISearchable
    {
        private readonly MainViewModel _mainVM;
        private readonly GameService _gameService;

        private ObservableCollection<Videojuego> _gamesList;
        private bool _isNintendo;
        private bool _isXbox;
        private bool _isPlayStation;
        private bool _isPC;
        private string _searchText;

        public ObservableCollection<Videojuego> GamesList { get => _gamesList; set => SetProperty(ref _gamesList, value); }

        public bool IsNintendo { get => _isNintendo; set => SetProperty(ref _isNintendo, value); }
        public bool IsXbox { get => _isXbox; set => SetProperty(ref _isXbox, value); }
        public bool IsPlayStation { get => _isPlayStation; set => SetProperty(ref _isPlayStation, value); }
        public bool IsPC { get => _isPC; set => SetProperty(ref _isPC, value); }


        public string SearchText
        {
            get => _searchText;
            set
            {
                // Actualizo el filtro en tiempo real al escribir sobre el.
                if (SetProperty(ref _searchText, value))
                {
                    ApplyFilters();
                }
            }
        }

        // Controlo la visibilidad de los filtros.
        private bool _isFiltersOpen = true;
        public bool IsFiltersOpen
        {
            get => _isFiltersOpen;
            set => SetProperty(ref _isFiltersOpen, value);
        }

        public RelayCommand SelectGameCommand { get; }

        // Para aplicar los filtros.
        public RelayCommand FilterCommand { get; }

        // Para limpiar los filtros seleccionados.
        public RelayCommand ClearFiltersCommand { get; }
        public RelayCommand ToggleFiltersCommand { get; }


        public GamesViewModel(MainViewModel mainVM)
        {
            _mainVM = mainVM;
            _gameService = new GameService();

            SelectGameCommand = new RelayCommand(param =>
            {
                if (param is Videojuego juego) _mainVM.CurrentView = new GameDetailViewModel(juego, _mainVM);
            });

            ToggleFiltersCommand = new RelayCommand(o => IsFiltersOpen = !IsFiltersOpen);

            // Ejecuto la lógica de filtrado.
            FilterCommand = new RelayCommand(o => ApplyFilters());

            // Reinicio los filtros y recargo la lista al completo.
            ClearFiltersCommand = new RelayCommand(o =>
            {
                IsNintendo = false;
                IsXbox = false;
                IsPlayStation = false;
                IsPC = false;
                SearchText = "";
                ApplyFilters();
            });

            LoadGames();
        }

        public GamesViewModel() { }

        // Cargo los juegos al iniciar.
        private void LoadGames() => ApplyFilters();

        private void ApplyFilters()
        {
            // Si no hay filtros seleccionados, muestro todos los juegos.
            string consola = "";
            if (IsNintendo) consola = "Nintendo";
            else if (IsPlayStation) consola = "Playstation";
            else if (IsXbox) consola = "Xbox";
            else if (IsPC) consola = "PC";

            var juegosFiltrados = _gameService.GetFilteredGames(null, consola, SearchText);
            GamesList = new ObservableCollection<Videojuego>(juegosFiltrados);
        }
    }
}
