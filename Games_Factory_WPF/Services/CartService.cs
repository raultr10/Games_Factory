using Games_Factory.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace Games_Factory.Services
{
    public class CartService
    {

        private readonly CartFileService _fileService;

        // Esta es la lista que verán las vistas.
        public ObservableCollection<CartItem> CartItems { get; private set; }

        public CartService()
        {
            _fileService = new CartFileService();
            CartItems = new ObservableCollection<CartItem>();
        }

        // Carga el carrito del archivo y verifica los precios en el SQL.
        public void LoadCartSecurely(string dni)
        {
            CartItems.Clear();
            var dtos = _fileService.LoadCart(dni);
            if (dtos.Count == 0) return;

            using (var ctx = new GameStoreContext())
            {
                foreach (var item in dtos)
                {
                    // Busca el juego real en la base de datos.
                    var juego = ctx.Videojuegos.Include("Producto").FirstOrDefault(v => v.IdProducto == item.IdProducto);
                    if (juego != null)
                    {
                        CartItems.Add(new CartItem { Videojuego = juego, Cantidad = item.Cantidad });
                    }
                }
            }
        }

        // Guarda el estado actual en el archivo encriptado.
        private void SaveCurrentCart(string dni)
        {
            var dtos = CartItems.Select(c => new CartItemDto { IdProducto = c.Videojuego.IdProducto, Cantidad = c.Cantidad }).ToList();
            _fileService.SaveCart(dni, dtos);
        }

        public void AddToCart(Videojuego juego, int cantidad, string dni)
        {
            var itemExistente = CartItems.FirstOrDefault(c => c.Videojuego.IdProducto == juego.IdProducto);
            if (itemExistente != null) itemExistente.Cantidad += cantidad;
            else CartItems.Add(new CartItem { Videojuego = juego, Cantidad = cantidad });

            SaveCurrentCart(dni);
        }

        public void RemoveFromCart(CartItem item, string dni)
        {
            CartItems.Remove(item);
            SaveCurrentCart(dni);
        }

        // Procesa el pago final en la Base de Datos y limpia la pantalla.
        public bool Checkout(string dni)
        {
            if (CartItems.Count == 0) return false;

            using (var ctx = new GameStoreContext())
            {
                foreach (var item in CartItems)
                {
                    ctx.UsuarioProductos.Add(new UsuarioProducto
                    {
                        IdDni = dni,
                        IdProducto = item.Videojuego.IdProducto,
                        Cantidad = item.Cantidad,
                        TotalPrecio = item.Subtotal
                    });
                }
                ctx.SaveChanges(); // Confirmar la compra en el SQL.
            }

            // Limpia el UI y el archivo.
            CartItems.Clear();
            _fileService.ClearCart(dni);

            return true;
        }

    }
}
