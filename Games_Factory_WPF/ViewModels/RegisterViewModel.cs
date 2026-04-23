using Games_Factory.Services;
using Games_Factory.Models;
using Games_Factory.ViewModels.Base;
using System;
using System.Windows;
using System.Windows.Controls;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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

        public string Dni { get => _dni; set => SetProperty(ref _dni, value); }
        public string Nombre { get => _nombre; set => SetProperty(ref _nombre, value); }
        public string Apellidos { get => _apellidos; set => SetProperty(ref _apellidos, value); }
        public string Direccion { get => _direccion; set => SetProperty(ref _direccion, value); }
        public DateTime FechaNacimiento { get => _fechaNacimiento; set => SetProperty(ref _fechaNacimiento, value); }
        public string Telefono { get => _telefono; set => SetProperty(ref _telefono, value); }
        public string CodigoPostal { get => _codigoPostal; set => SetProperty(ref _codigoPostal, value); }
        public string Correo { get => _correo; set => SetProperty(ref _correo, value); }

        public RelayCommand RegisterCommand { get; }
        public RelayCommand GoBackCommand { get; }

        public RegisterViewModel(MainViewModel mainVM)
        {
            _mainVM = mainVM;
            _authService = new AuthService();

            RegisterCommand = new RelayCommand(parameter =>
            {
                // Obtengo la contraseña de manera segura.
                var passwordBox = parameter as PasswordBox;
                string password = passwordBox?.Password ?? "";

                // Valido que los campos obligatorios no estén vacíos.
                if (string.IsNullOrWhiteSpace(Dni) ||
                    string.IsNullOrWhiteSpace(Nombre) ||
                    string.IsNullOrWhiteSpace(Correo) ||
                    string.IsNullOrWhiteSpace(password))
                {
                    MessageBox.Show("Por favor, rellena todos los campos obligatorios (DNI, Nombre, Correo y Contraseña).", "Datos Incompletos", MessageBoxButton.OK, MessageBoxImage.Warning);
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
    }
}
