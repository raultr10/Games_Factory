using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Globalization;
using System.Windows.Data;
using System.Windows.Media.Imaging;

namespace Games_Factory.Converters
{
    public class UrlImageConverter : IValueConverter
    {
        // Defino la URL del Servidor Web (Nginx).
        private const string BaseUrl = "http://localhost:8080/";

        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value is string imageName && !string.IsNullOrEmpty(imageName))
            {
                try
                {
                    string fullUrl = $"{BaseUrl}{imageName}";
                    BitmapImage image = new BitmapImage();

                    image.BeginInit();

                    image.UriSource = new Uri(fullUrl);
                    image.CacheOption = BitmapCacheOption.OnLoad;

                    image.EndInit();
                    return image;
                }
                catch
                {
                    return null;
                }
            }
            return null;
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture) => throw new NotImplementedException();
    }
}
