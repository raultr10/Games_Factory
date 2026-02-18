using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.Models
{
    public class Videojuego
    {
        [Key]
        [Column("ID_producto")]
        [StringLength(12)]
        public string IdProducto { get; set; } = null!;

        [Column("categoria_videojuego")]
        [StringLength(10)]
        public string Categoria { get; set; } = null!; // Plataforma, Acción, etc.

        [Column("tipo_consola")]
        [StringLength(11)]
        public string TipoConsola { get; set; } = null!; // Nintendo, Xbox, etc.

        [Column("idioma")]
        [StringLength(2)]
        public string Idioma { get; set; } = null!;

        [Column("compania")]
        [StringLength(30)]
        public string Compania { get; set; } = null!;

        // Navegación hacia el padre Producto
        [ForeignKey("IdProducto")]
        public virtual Producto Producto { get; set; } = null!;
    }
}
