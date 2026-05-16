using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Games_Factory.ViewModels.Base;

namespace Games_Factory.Models
{
    public class CartItem : ViewModelBase
    {

        private int _cantidad;
        public Videojuego Videojuego { get; set; }

        public int Cantidad
        {
            get => _cantidad;
            set
            {
                SetProperty(ref _cantidad, value);
                OnPropertyChanged(nameof(Subtotal));
            }
        }

        // Calcula el precio leyendo directamente el precio real/actual de la base de datos.
        public decimal Subtotal => Videojuego.Producto.Precio * Cantidad;

    }
}
