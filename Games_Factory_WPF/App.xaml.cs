using System.Configuration;
using System.Data;
using System.Windows;

namespace Games_Factory
{
    /// <summary>
    /// Interaction logic for App.xaml
    /// </summary>
    public partial class App : Application
    {
        public static Models.Usuario? CurrentUser { get; set; }

        public App()
        {
            // Me suscribo a los eventos de errores no controlados.
            this.DispatcherUnhandledException += App_DispatcherUnhandledException;
        }

        private void App_DispatcherUnhandledException(object sender, System.Windows.Threading.DispatcherUnhandledExceptionEventArgs e)
        {
            // Muestro el mensaje de error crítico al usuario.
            MessageBox.Show("Ocurrió un error fatal: " + e.Exception.Message + "\n\n" + e.Exception.InnerException?.Message, "Error Crítico");

            // Evito el cierre forzoso de la aplicación para permitir la depuración.
            e.Handled = true;
        }
    }

}
