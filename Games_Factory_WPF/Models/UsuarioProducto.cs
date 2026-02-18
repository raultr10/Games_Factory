using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.Models
{
    public class UsuarioProducto
    {
        [Column("ID_DNI")]
        [StringLength(11)]
        public string IdDni { get; set; } = null!;

        [Column("ID_producto")]
        [StringLength(12)]
        public string IdProducto { get; set; } = null!;

        [Column("cantidad")]
        public int Cantidad { get; set; }

        [Column("total_precio")]
        public decimal TotalPrecio { get; set; }

        [ForeignKey("IdDni")]
        public virtual Usuario Usuario { get; set; } = null!;

        [ForeignKey("IdProducto")]
        public virtual Producto Producto { get; set; } = null!;
    }
}
