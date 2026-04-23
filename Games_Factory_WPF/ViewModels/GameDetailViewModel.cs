using Games_Factory.Models;
using Games_Factory.ViewModels.Base;
using System;
using System.Windows;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.ViewModels
{
    public class GameDetailViewModel : ViewModelBase
    {
        private readonly MainViewModel _mainVM;
        private Videojuego _selectedGame;
        private int _cantidad = 1;

        public Videojuego SelectedGame { get => _selectedGame; set => SetProperty(ref _selectedGame, value); }
        public int Cantidad { get => _cantidad; set { if (value < 1) value = 1; SetProperty(ref _cantidad, value); } }
        public RelayCommand BuyCommand { get; }
        public RelayCommand GoBackCommand { get; }

        public GameDetailViewModel(Videojuego game, MainViewModel mainVM)
        {
            SelectedGame = game;
            _mainVM = mainVM;
            GoBackCommand = new RelayCommand(o => _mainVM.CurrentView = new GamesViewModel(_mainVM));

            // Verifico la sesión y luego proceso la compra.
            BuyCommand = new RelayCommand(o =>
            {
                if (App.CurrentUser == null)
                {
                    MessageBox.Show("Debes iniciar sesión para comprar.", "Atención", MessageBoxButton.OK, MessageBoxImage.Warning);
                    _mainVM.CurrentView = new LoginViewModel(_mainVM);
                    return;
                }

                decimal total = SelectedGame.Producto.Precio * Cantidad;
                if (MessageBox.Show($"¿Comprar {Cantidad}x {SelectedGame.Producto.Nombre} por {total:C}?", "Confirmar", MessageBoxButton.YesNo) == MessageBoxResult.Yes)
                {
                    using (var ctx = new GameStoreContext())
                    {
                        ctx.UsuarioProductos.Add(new UsuarioProducto
                        {
                            IdDni = App.CurrentUser.IdDni,
                            IdProducto = SelectedGame.IdProducto,
                            Cantidad = Cantidad,
                            TotalPrecio = total
                        });
                        ctx.SaveChanges();
                    }
                    MessageBox.Show("¡Compra realizada con éxito!");
                    _mainVM.CurrentView = new HomeViewModel(_mainVM);
                }
            });
        }
    }
}
