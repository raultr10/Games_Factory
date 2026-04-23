using Games_Factory.Models;
using System;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.Services
{
    public class GameService
    {
        // Recupero todos los videojuegos incluyendo su información de producto.
        public List<Videojuego> GetAllGames()
        {
            using (var context = new GameStoreContext())
            {
                return context.Videojuegos.Include(v => v.Producto).ToList();
            }
        }

        // Filtro los juegos según la categoría, consola y por el de búsqueda.
        public List<Videojuego> GetFilteredGames(string categoria, string consola, string busqueda)
        {
            using (var context = new GameStoreContext())
            {
                var query = context.Videojuegos.Include(v => v.Producto)
                                               .AsQueryable();

                if (!string.IsNullOrEmpty(categoria))
                {
                    query = query.Where(v => v.Categoria == categoria);
                }

                if (!string.IsNullOrEmpty(consola))
                {
                    query = query.Where(v => v.TipoConsola == consola);
                }

                if (!string.IsNullOrEmpty(busqueda))
                {
                    query = query.Where(v => v.Producto.Nombre.Contains(busqueda));
                }

                return query.ToList();
            }
        }
    }
}
