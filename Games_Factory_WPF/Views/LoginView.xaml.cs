using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Games_Factory.Views
{
    /// <summary>
    /// Lógica de interacción para LoginView.xaml
    /// </summary>
    public partial class LoginView : UserControl
    {
        public LoginView()
        {
            InitializeComponent();
        }

        // Sirve para alternar entre mostrar y ocultar la contraseña.
        private void btnShowPass_Click(object sender, RoutedEventArgs e)
        {
            if (btnShowPass.IsChecked == true)
            {
                // Modo contraseña visible.
                txtPassVisible.Text = txtPass.Password;
                txtPass.Visibility = Visibility.Collapsed;
                txtPassVisible.Visibility = Visibility.Visible;
            }
            else
            {
                // Modo contraseña oculta.
                txtPass.Password = txtPassVisible.Text;
                txtPassVisible.Visibility = Visibility.Collapsed;
                txtPass.Visibility = Visibility.Visible;
            }
        }

        // Sincroniza lo que el usuario escribe mientras el ojo está activado.
        private void txtPassVisible_TextChanged(object sender, TextChangedEventArgs e)
        {
            txtPass.Password = txtPassVisible.Text;
        }
    }
}
