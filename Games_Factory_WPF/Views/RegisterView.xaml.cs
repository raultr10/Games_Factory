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
    /// Lógica de interacción para RegisterView.xaml
    /// </summary>
    public partial class RegisterView : UserControl
    {
        public RegisterView()
        {
            InitializeComponent();
        }

        // Sirve para alternar entre mostrar y ocultar la contraseña de el Registro.
        private void btnShowRegPass_Click(object sender, RoutedEventArgs e)
        {
            if (btnShowRegPass.IsChecked == true)
            {
                // Modo contraseña visible.
                txtRegPassVisible.Text = txtRegPass.Password;
                txtRegPass.Visibility = Visibility.Collapsed;
                txtRegPassVisible.Visibility = Visibility.Visible;
            }
            else
            {
                // Modo contraseña oculta.
                txtRegPass.Password = txtRegPassVisible.Text;
                txtRegPassVisible.Visibility = Visibility.Collapsed;
                txtRegPass.Visibility = Visibility.Visible;
            }
        }

        // Sincroniza lo que el usuario escribe mientras el ojo está activado.
        private void txtRegPassVisible_TextChanged(object sender, TextChangedEventArgs e)
        {
            txtRegPass.Password = txtRegPassVisible.Text;
        }
    }
}
