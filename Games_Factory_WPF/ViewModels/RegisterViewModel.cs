using Games_Factory.Models;
using Games_Factory.Services;
using Games_Factory.Validations;
using Games_Factory.ViewModels.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;

namespace Games_Factory.ViewModels
{
    public class RegisterViewModel : ViewModelBase
    {
        private readonly MainViewModel _mainVM;
        private readonly AuthService _authService;

        // Defino los campos del formulario de registro.
        private string _dni;
        private string _nombre;
        private string _apellidos;
        private string _direccion;
        private DateTime _fechaNacimiento = DateTime.Now.AddYears(-18);
        private string _telefono;
        private string _codigoPostal;
        private string _correo;

        public string Dni
        {
            get => _dni;
            set { SetProperty(ref _dni, value); EjecutarValidarDni(); }
        }
        public string Nombre
        {
            get => _nombre;
            set { SetProperty(ref _nombre, value); EjecutarValidarNombre(); }
        }
        public string Apellidos
        {
            get => _apellidos;
            set { SetProperty(ref _apellidos, value); EjecutarValidarApellidos(); }
        }
        public string Direccion
        {
            get => _direccion;
            set { SetProperty(ref _direccion, value); EjecutarValidarDireccion(); }
        }
        public DateTime FechaNacimiento
        {
            get => _fechaNacimiento;
            set { SetProperty(ref _fechaNacimiento, value); EjecutarValidarFechaNacimiento(); }
        }
        public string Telefono
        {
            get => _telefono;
            set { SetProperty(ref _telefono, value); EjecutarValidarTelefono(); }
        }
        public string CodigoPostal
        {
            get => _codigoPostal;
            set { SetProperty(ref _codigoPostal, value); EjecutarValidarCodigoPostal(); }
        }
        public string Correo
        {
            get => _correo;
            set { SetProperty(ref _correo, value); EjecutarValidarCorreo(); }
        }

        // Notificación de la interfaz de un mensaje de error
        private string _errorDni;
        public string ErrorDni { get => _errorDni; set => SetProperty(ref _errorDni, value); }

        private string _errorNombre;
        public string ErrorNombre { get => _errorNombre; set => SetProperty(ref _errorNombre, value); }

        private string _errorApellidos;
        public string ErrorApellidos { get => _errorApellidos; set => SetProperty(ref _errorApellidos, value); }

        private string _errorDireccion;
        public string ErrorDireccion { get => _errorDireccion; set => SetProperty(ref _errorDireccion, value); }

        private string _errorFecha;
        public string ErrorFecha { get => _errorFecha; set => SetProperty(ref _errorFecha, value); }

        private string _errorTelefono;
        public string ErrorTelefono { get => _errorTelefono; set => SetProperty(ref _errorTelefono, value); }

        private string _errorCodigoPostal;
        public string ErrorCodigoPostal { get => _errorCodigoPostal; set => SetProperty(ref _errorCodigoPostal, value); }

        private string _errorCorreo;
        public string ErrorCorreo { get => _errorCorreo; set => SetProperty(ref _errorCorreo, value); }

        private string _errorContrasena;
        public string ErrorContrasena { get => _errorContrasena; set => SetProperty(ref _errorContrasena, value); }


        public RelayCommand RegisterCommand { get; }
        public RelayCommand GoBackCommand { get; }

        public RegisterViewModel(MainViewModel mainVM)
        {
            _mainVM = mainVM;
            _authService = new AuthService();

            RegisterCommand = new RelayCommand(parameter =>
            {
                var passwordBox = parameter as PasswordBox;
                string password = passwordBox?.Password ?? "";

                bool vDni = EjecutarValidarDni();
                bool vNom = EjecutarValidarNombre();
                bool vApe = EjecutarValidarApellidos();
                bool vDir = EjecutarValidarDireccion();
                bool vFec = EjecutarValidarFechaNacimiento();
                bool vTel = EjecutarValidarTelefono();
                bool vCp = EjecutarValidarCodigoPostal();
                bool vCor = EjecutarValidarCorreo();
                bool vPass = EjecutarValidarContrasena(password);

                // Detenemos el proceso antes de enviarlo a la base de datos
                if (!vDni || !vNom || !vApe || !vDir || !vFec || !vTel || !vCp || !vCor || !vPass)
                {
                    MessageBox.Show("Por favor, corrige los campos marcados antes de continuar.", "Datos Inválidos", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }

                // Creo la nueva instancia del usuario con los datos ingresados.
                var newUser = new Usuario
                {
                    IdDni = Dni,
                    Nombre = Nombre,
                    Apellidos = Apellidos,
                    Direccion = Direccion,
                    FechaNacimiento = FechaNacimiento,
                    Telefono = Telefono,
                    CodigoPostal = CodigoPostal,
                    Correo = Correo,
                    // El servicio se encargará de cifrar la contraseña.
                    Contrasena = password,
                };

                // Intento registrar el usuario en la base de datos.
                if (_authService.RegisterUser(newUser))
                {
                    MessageBox.Show("¡Registro completado con éxito! Ahora puedes iniciar sesión.", "Bienvenido");

                    // Redirijo al usuario a la pantalla de inicio de sesión.
                    _mainVM.CurrentView = new LoginViewModel(_mainVM);
                }
                else
                {
                    MessageBox.Show("Error al registrar el usuario.\nEs posible que el DNI o el Correo ya existan.", "Error de Registro", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            });

            GoBackCommand = new RelayCommand(o => _mainVM.CurrentView = new LoginViewModel(_mainVM));
        }

        // Metodos de validación: 
        private bool EjecutarValidarDni()
        {
            var result = ValidationRules.ValidarDni(Dni);
            ErrorDni = result.Message;
            return result.IsValid;
        }

        private bool EjecutarValidarNombre()
        {
            var result = ValidationRules.ValidarNombre(Nombre);
            ErrorNombre = result.Message;
            return result.IsValid;
        }

        private bool EjecutarValidarApellidos()
        {
            var result = ValidationRules.ValidarApellidos(Apellidos);
            ErrorApellidos = result.Message;
            return result.IsValid;
        }

        private bool EjecutarValidarDireccion()
        {
            var result = ValidationRules.ValidarDireccion(Direccion);
            ErrorDireccion = result.Message;
            return result.IsValid;
        }

        private bool EjecutarValidarFechaNacimiento()
        {
            var result = ValidationRules.ValidarFechaNacimiento(FechaNacimiento);
            ErrorFecha = result.Message;
            return result.IsValid;
        }

        private bool EjecutarValidarTelefono()
        {
            var result = ValidationRules.ValidarTelefono(Telefono);
            ErrorTelefono = result.Message;
            return result.IsValid;
        }

        private bool EjecutarValidarCodigoPostal()
        {
            var result = ValidationRules.ValidarCodigoPostal(CodigoPostal);
            ErrorCodigoPostal = result.Message;
            return result.IsValid;
        }

        private bool EjecutarValidarCorreo()
        {
            var result = ValidationRules.ValidarCorreo(Correo);
            ErrorCorreo = result.Message;
            return result.IsValid;
        }

        private bool EjecutarValidarContrasena(string password)
        {
            var result = ValidationRules.ValidarContrasena(password);
            ErrorContrasena = result.Message;
            return result.IsValid;
        }
    }
}
