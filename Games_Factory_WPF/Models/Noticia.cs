using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.Models
{
    public class Noticia
    {
        [Key]
        [Column("ID_noticia")]
        [StringLength(12)]
        public string IdNoticia { get; set; } = null!;

        [Column("titulo")]
        [StringLength(60)]
        public string Titulo { get; set; } = null!;

        [Column("descripcion")]
        [StringLength(380)]
        public string Descripcion { get; set; } = null!;

        [Column("historia")]
        [StringLength(680)]
        public string Historia { get; set; } = null!;

        [Column("fecha_creacion")]
        public DateTime FechaCreacion { get; set; }

        [Column("categoria_noticia")]
        [StringLength(11)]
        public string CategoriaNoticia { get; set; } = null!; // Playstation, Nintendo, etc.

        [Column("imagen")]
        [StringLength(90)]
        public string Imagen { get; set; } = null!;
    }
}
