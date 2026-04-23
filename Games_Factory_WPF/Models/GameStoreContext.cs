using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Emit;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace Games_Factory.Models
{
    public class GameStoreContext : DbContext
    {
        public DbSet<Empleado> Empleados { get; set; }
        public DbSet<CategoriaEmpleado> CategoriaEmpleados { get; set; }
        public DbSet<Noticia> Noticias { get; set; }
        public DbSet<Producto> Productos { get; set; }
        public DbSet<Videojuego> Videojuegos { get; set; }
        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<UsuarioProducto> UsuarioProductos { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseSqlServer("Server=localhost,1433;Database=Games_Factory;User Id=sa;Password=Password123!;TrustServerCertificate=True;");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<CategoriaEmpleado>(e => { e.HasKey(x => x.IdEmp); e.ToTable("Categoria_Empleado"); });
            modelBuilder.Entity<Empleado>(e => { e.HasKey(x => x.IdEmp); e.ToTable("Empleado"); e.Property(x => x.IdEmp).IsFixedLength(); });
            modelBuilder.Entity<Noticia>(e => { e.HasKey(x => x.IdNoticia); e.ToTable("Noticia"); e.Property(x => x.IdNoticia).IsFixedLength(); });

            modelBuilder.Entity<Producto>(e =>
            {
                e.HasKey(x => x.IdProducto);
                e.ToTable("Producto");
                e.Property(x => x.IdProducto).IsFixedLength();
                e.Property(x => x.Precio).HasColumnType("decimal(5, 2)");
            });

            modelBuilder.Entity<Videojuego>(e =>
            {
                e.HasKey(x => x.IdProducto);
                e.ToTable("Videojuego");
                e.Property(x => x.IdProducto).IsFixedLength();
                e.HasOne(d => d.Producto).WithOne(p => p.VideojuegoInfo).HasForeignKey<Videojuego>(d => d.IdProducto);
            });

            modelBuilder.Entity<Usuario>(e => { e.HasKey(x => x.IdDni); e.ToTable("Usuario"); e.Property(x => x.IdDni).IsFixedLength(); });

            modelBuilder.Entity<UsuarioProducto>(e =>
            {
                e.ToTable("Usuario_Producto");
                e.HasKey(x => new { x.IdDni, x.IdProducto });
                e.Property(x => x.TotalPrecio).HasColumnType("decimal(5, 2)");
                e.HasOne(d => d.Usuario).WithMany(p => p.Compras).HasForeignKey(d => d.IdDni);
                e.HasOne(d => d.Producto).WithMany(p => p.Ventas).HasForeignKey(d => d.IdProducto);
            });
        }
    }
}
