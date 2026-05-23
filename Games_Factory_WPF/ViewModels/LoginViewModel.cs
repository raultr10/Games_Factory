using Games_Factory.Services;
using Games_Factory.ViewModels.Base;
using Games_Factory.Validations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;

namespace Games_Factory.ViewModels
{
    public class LoginViewModel : ViewModelBase
    {
        private readonly MainViewModel _mainVM;
        private readonly AuthService _authService;
        private string _email;
        private string _errorCorreo;

        public string Email { get => _email; set => SetProperty(ref _email, value); }
        public string ErrorCorreo { get => _errorCorreo; set => SetProperty(ref _errorCorreo, value); }

        public RelayCommand LoginCommand { get; }
        public RelayCommand RegisterNavCommand { get; }

        public LoginViewModel(MainViewModel mainVM)
        {
            _mainVM = mainVM;
            _authService = new AuthService();

            // Recibo el PasswordBox como parámetro para el login.
            LoginCommand = new RelayCommand(parameter =>
            {
                var validacion = ValidationRules.ValidarCorreo(Email);
                if (!validacion.IsValid)
                {
                    ErrorCorreo = validacion.Message;
                    return;
                }

                // Si es correcto, limpiamos el mensaje de error por si había uno antes
                ErrorCorreo = string.Empty;

                var passwordBox = parameter as PasswordBox;
                string password = passwordBox?.Password ?? "";

                var user = _authService.Login(Email, password);
                if (user != null)
                {
                    App.CurrentUser = user;
                    _mainVM.UpdateSessionStatus();
                    MessageBox.Show($"Bienvenido, {user.Nombre}!");
                    _mainVM.CurrentView = new HomeViewModel(_mainVM);
                }
                else
                {
                    MessageBox.Show("Correo o contraseña incorrectos.", "Error Login");
                }
            });

            RegisterNavCommand = new RelayCommand(o => _mainVM.CurrentView = new RegisterViewModel(_mainVM));
        }
    }
}
