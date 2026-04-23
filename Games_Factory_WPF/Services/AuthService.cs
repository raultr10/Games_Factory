using Games_Factory.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Security.Cryptography;

namespace Games_Factory.Services
{
    public class AuthService
    {
        // Intento de iniciar sesión con el correo y contraseña que proporciona el usuario.
        public Usuario? Login(string email, string password)
        {
            using (var context = new GameStoreContext())
            {
                var user = context.Usuarios.FirstOrDefault(u => u.Correo == email);
                if (user == null)
                {
                    return null;
                }

                // Verifico que la contraseña coincida, comparando los hashes.
                string passwordHash = ComputeSha256Hash(password);
                if (user.Contrasena == passwordHash)
                {
                    return user;
                }

                return null;
            }
        }

        // Registro un nuevo usuario si no existe previamente.
        public bool RegisterUser(Usuario newUser)
        {
            using (var context = new GameStoreContext())
            {
                if (context.Usuarios.Any(u => u.IdDni == newUser.IdDni || u.Correo == newUser.Correo))
                {
                    return false;
                }

                newUser.Contrasena = ComputeSha256Hash(newUser.Contrasena);
                context.Usuarios.Add(newUser);
                context.SaveChanges();
                return true;
            }
        }

        private string ComputeSha256Hash(string rawData)
        {
            using (SHA256 sha256Hash = SHA256.Create())
            {
                byte[] bytes = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(rawData));
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < bytes.Length; i++)
                {
                    builder.Append(bytes[i].ToString("X2"));
                }
                return builder.ToString();
            }
        }
    }
}
