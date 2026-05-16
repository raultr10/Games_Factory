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
    /// Lógica de interacción para HomeView.xaml
    /// </summary>
    public partial class HomeView : UserControl
    {
        public HomeView()
        {
            InitializeComponent();
        }

        private void UserControl_SizeChanged(object sender, SizeChangedEventArgs e)
        {
            // Si hay poco espacio se colocarán verticalmente los botones.
            if (e.NewSize.Width < 900)
            {
                Col1.Width = new GridLength(0);
                Row1.Height = new GridLength(1, GridUnitType.Star);

                Grid.SetColumn(btnNews, 0);
                Grid.SetRow(btnNews, 1);
            }
            else
            {
                Col1.Width = new GridLength(1, GridUnitType.Star);
                Row1.Height = new GridLength(0);
                Grid.SetColumn(btnNews, 1);
                Grid.SetRow(btnNews, 0);
            }
        }
    }
}
