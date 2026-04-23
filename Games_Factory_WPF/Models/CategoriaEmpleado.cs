using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.Models
{
    public class CategoriaEmpleado
    {
        [Key]
        [Column("ID_emp")]
        [StringLength(11)]
        public string IdEmp { get; set; } = null!;

        [Column("tipo_empleado")]
        [StringLength(15)]
        public string TipoEmpleado { get; set; } = null!;

        [ForeignKey("IdEmp")]
        public virtual Empleado Empleado { get; set; } = null!;
    }
}
