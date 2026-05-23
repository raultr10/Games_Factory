using System;
using System.Text.RegularExpressions;

namespace Games_Factory.Validations
{
    public static class ValidationRules
    {
        public static (bool IsValid, string Message) ValidarDni(string dni)
        {
            if (string.IsNullOrWhiteSpace(dni))
            {
                return (false, "El DNI es obligatorio.");
            }

            var regex = new Regex(@"^[0-9]{2}\.[0-9]{3}\.[0-9]{3}[A-Z]$");
            if (!regex.IsMatch(dni))
            {
                return (false, "Formato requerido: XX.XXX.XXXA");
            }

            return (true, string.Empty);
        }

        public static (bool IsValid, string Message) ValidarNombre(string nombre)
        {
            if (string.IsNullOrWhiteSpace(nombre))
            {
                return (false, "El nombre es obligatorio.");
            }

            if (nombre.Length > 30)
            {
                return (false, "El nombre no puede superar los 30 caracteres.");
            }

            return (true, string.Empty);
        }

        public static (bool IsValid, string Message) ValidarApellidos(string apellidos)
        {
            if (string.IsNullOrWhiteSpace(apellidos))
            {
                return (false, "Los apellidos son obligatorios.");
            }

            if (apellidos.Length > 50)
            {
                return (false, "Los apellidos no pueden superar los 50 caracteres.");
            }

            return (true, string.Empty);
        }

        public static (bool IsValid, string Message) ValidarDireccion(string direccion)
        {
            if (string.IsNullOrWhiteSpace(direccion))
            {
                return (false, "La dirección completa es obligatoria.");
            }

            if (direccion.Length > 55)
            {
                return (false, "La dirección no puede superar los 55 caracteres.");
            }

            return (true, string.Empty);
        }

        public static (bool IsValid, string Message) ValidarFechaNacimiento(DateTime fecha)
        {
            if (fecha.Date > DateTime.Today)
            {
                return (false, "La fecha no puede ser mayor al día de hoy.");
            }

            return (true, string.Empty);
        }

        public static (bool IsValid, string Message) ValidarTelefono(string telefono)
        {
            if (string.IsNullOrWhiteSpace(telefono))
            {
                return (false, "El teléfono es obligatorio.");
            }

            var regex = new Regex(@"^[0-9]{9}$");
            if (!regex.IsMatch(telefono))
            {
                return (false, "El teléfono debe tener exactamente 9 números.");
            }

            return (true, string.Empty);
        }

        public static (bool IsValid, string Message) ValidarCodigoPostal(string cp)
        {
            if (string.IsNullOrWhiteSpace(cp))
            {
                return (false, "El código postal es obligatorio.");
            }

            var regex = new Regex(@"^[0-9]{5}$");
            if (!regex.IsMatch(cp))
            {
                return (false, "El código postal debe tener exactamente 5 números.");
            }

            return (true, string.Empty);
        }

        public static (bool IsValid, string Message) ValidarCorreo(string correo)
        {
            if (string.IsNullOrWhiteSpace(correo))
            {
                return (false, "El correo electrónico es obligatorio.");
            }

            if (correo.Length > 55)
            {
                return (false, "El correo no puede superar los 55 caracteres.");
            }

            var regex = new Regex(@"^[^@\s]+@[^@\s]+\.[^@\s]+$");
            if (!regex.IsMatch(correo))
            {
                return (false, "Formato de correo electrónico no válido.");
            }

            return (true, string.Empty);
        }

        public static (bool IsValid, string Message) ValidarContrasena(string contrasena)
        {
            if (string.IsNullOrWhiteSpace(contrasena))
            {
                return (false, "La contraseña es obligatoria.");
            }

            if (contrasena.Length < 12) 
            {
                return (false, "La contraseña debe tener al menos 12 caracteres.");
            }

            if (contrasena.Length > 150)
            {
                return (false, "La contraseña no debe tener mas de 150 caracteres.");
            }

            return (true, string.Empty);
        }

        public static (bool IsValid, string Message) ValidarCantidadCompra(string cantidadTexto, int maximoPermitido = 20)
        {
            if (string.IsNullOrWhiteSpace(cantidadTexto))
            {
                return (false, "La cantidad no puede estar vacía.");
            }

            if (!int.TryParse(cantidadTexto, out int cantidad))
            {
                return (false, "Debes introducir un número entero válido.");
            }

            if (cantidad <= 0)
            {
                return (false, "La cantidad debe ser mayor que cero.");
            }

            if (cantidad > maximoPermitido)
            {
                return (false, $"No puedes comprar más de {maximoPermitido} unidades de golpe.");
            }

            return (true, string.Empty);
        }
    }
}