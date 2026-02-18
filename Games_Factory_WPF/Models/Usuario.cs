using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.Models
{
    public class Usuario
    {
        [Key]
        [Column("ID_DNI")]
        [StringLength(11)]
        public string IdDni { get; set; } = null!;

        [Column("nombre_usu")]
        [StringLength(30)]
        public string Nombre { get; set; } = null!;

        [Column("apellidos_usu")]
        [StringLength(50)]
        public string Apellidos { get; set; } = null!;

        [Column("direccion")]
        [StringLength(55)]
        public string Direccion { get; set; } = null!;

        [Column("fecha_naci")]
        public DateTime FechaNacimiento { get; set; }

        [Column("telefono")]
        [StringLength(9)]
        public string Telefono { get; set; } = null!;

        [Column("codigo_postal")]
        [StringLength(5)]
        public string CodigoPostal { get; set; } = null!;

        [Column("correo_usu")]
        [StringLength(55)]
        public string Correo { get; set; } = null!;

        [Column("contrasena_usu")]
        [StringLength(150)]
        public string Contrasena { get; set; } = null!;

        // Navegación
        public virtual ICollection<UsuarioProducto> Compras { get; set; } = new List<UsuarioProducto>();
    }
}
