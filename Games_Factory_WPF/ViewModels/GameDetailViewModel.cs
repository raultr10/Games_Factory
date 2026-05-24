using Games_Factory.Models;
using Games_Factory.Validations;
using Games_Factory.ViewModels.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace Games_Factory.ViewModels
{
    public class GameDetailViewModel : ViewModelBase
    {
        private readonly MainViewModel _mainVM;
        private Videojuego _selectedGame;
        private int _cantidad = 1;

        public Videojuego SelectedGame { get => _selectedGame; set => SetProperty(ref _selectedGame, value); }
        public int Cantidad { get => _cantidad; set { SetProperty(ref _cantidad, value); } }

        public RelayCommand AddToCartCommand { get; }
        public RelayCommand BuyNowCommand { get; }
        public RelayCommand GoBackCommand { get; }

        public GameDetailViewModel(Videojuego game, MainViewModel mainVM)
        {
            SelectedGame = game;
            _mainVM = mainVM;
            GoBackCommand = new RelayCommand(o => _mainVM.CurrentView = new GamesViewModel(_mainVM));

            AddToCartCommand = new RelayCommand(o =>
            {
                if (App.CurrentUser == null)
                {
                    MessageBox.Show("Debes iniciar sesión para usar el carrito.", "Atención", MessageBoxButton.OK, MessageBoxImage.Warning);
                    _mainVM.CurrentView = new LoginViewModel(_mainVM);
                    return;
                }

                var validacion = ValidationRules.ValidarCantidadCompra(Cantidad.ToString(), 20);
                if (!validacion.IsValid)
                {
                    MessageBox.Show(validacion.Message, "Cantidad No Válida.", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }

                _mainVM.CartService.AddToCart(SelectedGame, Cantidad, App.CurrentUser.IdDni);
                MessageBox.Show("Añadido al carrito correctamente.", "Éxito", MessageBoxButton.OK, MessageBoxImage.Information);
            });

            BuyNowCommand = new RelayCommand(async o =>
            {
                if (App.CurrentUser == null)
                {
                    MessageBox.Show("Debes iniciar sesión para comprar.", "Atención", MessageBoxButton.OK, MessageBoxImage.Warning);
                    _mainVM.CurrentView = new LoginViewModel(_mainVM);
                    return;
                }

                var validacion = ValidationRules.ValidarCantidadCompra(Cantidad.ToString(), 20);
                if (!validacion.IsValid)
                {
                    MessageBox.Show(validacion.Message, "Cantidad No Válida.", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return; 
                }

                decimal total = SelectedGame.Producto.Precio * Cantidad;

                if (MessageBox.Show($"¿Comprar {Cantidad} {SelectedGame.Producto.Nombre} por {total:C}?", "Confirmar", MessageBoxButton.YesNo) == MessageBoxResult.Yes)
                {
                    try
                    {
                        await Task.Run(() =>
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
                        });

                        MessageBox.Show("¡Compra realizada con éxito!");
                        _mainVM.CurrentView = new HomeViewModel(_mainVM);
                    }
                    catch (Exception)
                    {
                        MessageBox.Show("No se ha podido conectar a la base de datos.", "Sin conexión", MessageBoxButton.OK, MessageBoxImage.Warning);
                    }
                }
            });
        }
    }
}
