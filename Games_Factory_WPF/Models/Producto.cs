using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.Models
{
    public class Producto
    {
        [Key]
        [Column("ID_producto")]
        [StringLength(12)]
        public string IdProducto { get; set; } = null!;

        [Column("descripcion")]
        [StringLength(900)]
        public string Descripcion { get; set; } = null!;

        [Column("nombre_prod")]
        [StringLength(90)]
        public string Nombre { get; set; } = null!;

        [Column("precio")]
        public decimal Precio { get; set; }

        [Column("anyo")]
        public int Anyo { get; set; }

        [Column("imagen")]
        [StringLength(90)]
        public string Imagen { get; set; } = null!;

        // Navegación (Relación 1 a 1 con Videojuego).
        public virtual Videojuego? VideojuegoInfo { get; set; }

        // Relación con ventas.
        public virtual ICollection<UsuarioProducto> Ventas { get; set; } = new List<UsuarioProducto>();
    }
}
