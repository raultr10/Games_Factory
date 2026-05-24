using Games_Factory.Models;
using Games_Factory.ViewModels.Base;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace Games_Factory.ViewModels
{
    public class CartViewModel : ViewModelBase
    {

        private readonly MainViewModel _mainVM;

        // Leemos la lista desde el servicio.
        public ObservableCollection<CartItem> Items => _mainVM.CartService.CartItems;
        public decimal TotalAmount => Items.Sum(i => i.Subtotal);

        public RelayCommand CheckoutCommand { get; }
        public RelayCommand RemoveItemCommand { get; }
        public RelayCommand ContinueShoppingCommand { get; }

        public CartViewModel(MainViewModel mainVM)
        {
            _mainVM = mainVM;

            // Escucha los cambios para actualizar el total.
            _mainVM.CartService.CartItems.CollectionChanged += (s, e) => OnPropertyChanged(nameof(TotalAmount));

            ContinueShoppingCommand = new RelayCommand(o => _mainVM.CurrentView = new GamesViewModel(_mainVM));

            RemoveItemCommand = new RelayCommand(param =>
            {
                if (param is CartItem item)
                {
                    _mainVM.CartService.RemoveFromCart(item, App.CurrentUser.IdDni);    
                    OnPropertyChanged(nameof(TotalAmount));
                }
            });

            CheckoutCommand = new RelayCommand(async o =>
            {
                if (Items.Count == 0) return;

                if (MessageBox.Show($"¿Realizar el pago seguro de {TotalAmount:C}?", "Finalizar Compra", MessageBoxButton.YesNo) == MessageBoxResult.Yes)
                {
                    try
                    {
                        bool completado = await Task.Run(() => _mainVM.CartService.Checkout(App.CurrentUser.IdDni));
                        
                        // Delegamos todo al servicio.
                        if (completado)
                        {
                            MessageBox.Show("¡Pago completado!");
                            _mainVM.CurrentView = new HomeViewModel(_mainVM);
                        }
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
