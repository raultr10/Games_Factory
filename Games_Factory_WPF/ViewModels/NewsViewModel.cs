using Games_Factory.Models;
using Games_Factory.ViewModels.Base;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.ViewModels
{
    public class NewsViewModel : ViewModelBase, ISearchable
    {
        private ObservableCollection<Noticia> _newsList;

        // Mantengo una copia de todas las noticias.
        private List<Noticia> _allNews;
        private Noticia _selectedNews;
        private string _searchText;

        // Defino las propiedades para filtrar por categoría.
        private bool _isCatPlaystation;
        private bool _isCatNintendo;
        private bool _isCatXbox;
        private bool _isCatPC;
        private bool _isWideLayout = true;

        public bool IsCatPlaystation { get => _isCatPlaystation; set => SetProperty(ref _isCatPlaystation, value); }
        public bool IsCatNintendo { get => _isCatNintendo; set => SetProperty(ref _isCatNintendo, value); }
        public bool IsCatXbox { get => _isCatXbox; set => SetProperty(ref _isCatXbox, value); }
        public bool IsCatPC { get => _isCatPC; set => SetProperty(ref _isCatPC, value); }

        public string SearchText
        {
            get => _searchText;
            set { if (SetProperty(ref _searchText, value)) _ = ApplyFilters(); }
        }

        private bool _isFiltersOpen = true;

        public bool IsFiltersOpen
        {
            get => _isFiltersOpen;
            set => SetProperty(ref _isFiltersOpen, value);
        }

        public bool IsWideLayout
        {
            get => _isWideLayout;
            set { _isWideLayout = value; OnPropertyChanged(nameof(IsWideLayout)); }
        }

        public ObservableCollection<Noticia> NewsList { get => _newsList; set => SetProperty(ref _newsList, value); }
        public Noticia SelectedNews { get => _selectedNews; set => SetProperty(ref _selectedNews, value); }

        public RelayCommand SelectNewsCommand { get; }
        public RelayCommand BackToListCommand { get; }
        public RelayCommand FilterCommand { get; }
        public RelayCommand ClearCommand { get; }
        public RelayCommand ToggleFiltersCommand { get; }


        public NewsViewModel()
        {
            _allNews = new List<Noticia>();

            NewsList = new ObservableCollection<Noticia>(_allNews);

            SelectNewsCommand = new RelayCommand(param => { if (param is Noticia n) SelectedNews = n; });
            BackToListCommand = new RelayCommand(o => SelectedNews = null);

            // Configuro el comando de filtrado.
            FilterCommand = new RelayCommand(o => _ = ApplyFilters());

            // Configuro el comando para restablecer filtros.
            ClearCommand = new RelayCommand(o =>
            {
                IsCatPlaystation = false;
                IsCatNintendo = false;
                IsCatXbox = false;
                IsCatPC = false;
                SearchText = "";
                _ = ApplyFilters();
            });

            ToggleFiltersCommand = new RelayCommand(o => IsFiltersOpen = !IsFiltersOpen);

            _ = ApplyFilters();
        }

        private async Task ApplyFilters()
        {

            try
            {
                // Cargo las noticias desde la base de datos.
                _allNews = await Task.Run(() =>
                {
                    using (var ctx = new GameStoreContext())
                    {
                        return ctx.Noticias.OrderByDescending(n => n.FechaCreacion).ToList();
                    }
                });

                // Inicio la consulta con todas las noticias disponibles.
                IEnumerable<Noticia> query = _allNews;

                // Filtro los resultados según el texto de búsqueda.
                if (!string.IsNullOrWhiteSpace(SearchText))
                {
                    string txt = SearchText.ToLower();
                    query = query.Where(n => n.Titulo.ToLower().Contains(txt) || n.Descripcion.ToLower().Contains(txt));
                }

                // Aplico el filtro si hay una categoría seleccionada.
                if (IsCatPlaystation) { query = query.Where(n => n.CategoriaNoticia == "Playstation"); }
                else if (IsCatNintendo) { query = query.Where(n => n.CategoriaNoticia == "Nintendo"); }
                else if (IsCatXbox) { query = query.Where(n => n.CategoriaNoticia == "Xbox"); }
                else if (IsCatPC) { query = query.Where(n => n.CategoriaNoticia == "PC"); }

                NewsList = new ObservableCollection<Noticia>(query.ToList());
            }
            catch (Exception)
            {
                _allNews = new List<Noticia>();
                NewsList = new ObservableCollection<Noticia>();
            }
        }
    }
}