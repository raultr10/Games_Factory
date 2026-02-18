using Games_Factory.ViewModels.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Games_Factory.ViewModels
{
    public class HomeViewModel : ViewModelBase
    {
        private readonly MainViewModel _mainVM;

        public RelayCommand GoToGamesCommand { get; }
        public RelayCommand GoToNewsCommand { get; }

        public HomeViewModel(MainViewModel mainVM)
        {
            _mainVM = mainVM;

            // Configuro la navegación desde los botones principales.
            GoToGamesCommand = new RelayCommand(o => _mainVM.CurrentView = new GamesViewModel(_mainVM));
            GoToNewsCommand = new RelayCommand(o => _mainVM.CurrentView = new NewsViewModel());
        }
    }
}
