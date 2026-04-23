using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.Models
{
    public class Empleado
    {
        [Key]
        [StringLength(11)]
        [Column("ID_emp")]
        public string IdEmp { get; set; } = null!;

        [Column("nombre_emp")]
        [StringLength(30)]
        public string Nombre { get; set; } = null!;

        [Column("apellidos_emp")]
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

        [Column("correo_emp")]
        [StringLength(55)]
        public string Correo { get; set; } = null!;

        [Column("contrasena_emp")]
        [StringLength(150)]
        public string Contrasena { get; set; } = null!;

        public virtual ICollection<CategoriaEmpleado> Categorias { get; set; } = new List<CategoriaEmpleado>();
    }
}
