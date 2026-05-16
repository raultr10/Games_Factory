using Games_Factory.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace Games_Factory.Services
{
    public class CartFileService
    {

        // Llaves dinámicas protegidas por DPAPI de Windows.
        private byte[] _key;
        private byte[] _iv;

        public CartFileService()
        {
            InitializeSecureKeys();
        }

        // Obtiene el directorio de guardado en la carpeta del usuario.
        private string GetFolderPath()
        {
            string folder = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData), "GamesFactory");

            if (!Directory.Exists(folder))
            {
                Directory.CreateDirectory(folder);
            }

            return folder;
        }

        // Obtiene la ruta completa del archivo del carrito según el DNI.
        private string GetFilePath(string dni)
        {
            return Path.Combine(GetFolderPath(), $"cart_{dni}.dat");
        }

        // Inicializa, genera y protege las claves de encriptación.
        private void InitializeSecureKeys()
        {
            string keyPath = Path.Combine(GetFolderPath(), "security_k.dat");
            string ivPath = Path.Combine(GetFolderPath(), "security_i.dat");

            // Intenta cargar las claves existentes.
            if (File.Exists(keyPath) && File.Exists(ivPath))
            {
                try
                {
                    byte[] protectedKey = File.ReadAllBytes(keyPath);
                    byte[] protectedIv = File.ReadAllBytes(ivPath);

                    _key = ProtectedData.Unprotect(protectedKey, null, DataProtectionScope.CurrentUser);
                    _iv = ProtectedData.Unprotect(protectedIv, null, DataProtectionScope.CurrentUser);
                    return;
                }
                catch
                {
                    // Falla silenciosa para regenerar claves si es necesario.
                }
            }

            // Genera nuevas claves AES.
            using (Aes aesAlg = Aes.Create())
            {
                aesAlg.GenerateKey();
                aesAlg.GenerateIV();
                _key = aesAlg.Key;
                _iv = aesAlg.IV;
            }

            // Protege y guarda las nuevas claves en disco.
            byte[] protectedNewKey = ProtectedData.Protect(_key, null, DataProtectionScope.CurrentUser);
            byte[] protectedNewIv = ProtectedData.Protect(_iv, null, DataProtectionScope.CurrentUser);

            File.WriteAllBytes(keyPath, protectedNewKey);
            File.WriteAllBytes(ivPath, protectedNewIv);
        }

        // Guarda el carrito serializado y encriptado en disco.
        public void SaveCart(string dni, List<CartItemDto> items)
        {
            try
            {
                string json = JsonSerializer.Serialize(items);
                byte[] encryptedData = Encrypt(json);
                File.WriteAllBytes(GetFilePath(dni), encryptedData);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine("Error al guardar el carrito: " + ex.Message);
            }
        }

        // Carga y desencripta el carrito desde el disco.
        public List<CartItemDto> LoadCart(string dni)
        {
            string path = GetFilePath(dni);

            if (!File.Exists(path))
            {
                return new List<CartItemDto>();
            }

            try
            {
                byte[] encryptedData = File.ReadAllBytes(path);
                string json = Decrypt(encryptedData);
                return JsonSerializer.Deserialize<List<CartItemDto>>(json) ?? new List<CartItemDto>();
            }
            catch
            {
                // Elimina el archivo en caso de corrupción o de manipulación no autorizada.
                File.Delete(path);
                return new List<CartItemDto>();
            }
        }

        // Elimina el archivo físico del carrito.
        public void ClearCart(string dni)
        {
            string path = GetFilePath(dni);

            if (File.Exists(path))
            {
                File.Delete(path);
            }
        }

        // Encripta una cadena de texto a un array de bytes usando AES.
        private byte[] Encrypt(string plainText)
        {
            using (Aes aesAlg = Aes.Create())
            {
                aesAlg.Key = _key;
                aesAlg.IV = _iv;

                ICryptoTransform encryptor = aesAlg.CreateEncryptor(aesAlg.Key, aesAlg.IV);
                using (MemoryStream msEncrypt = new MemoryStream())
                {
                    using (CryptoStream csEncrypt = new CryptoStream(msEncrypt, encryptor, CryptoStreamMode.Write))
                    using (StreamWriter swEncrypt = new StreamWriter(csEncrypt))
                    {
                        swEncrypt.Write(plainText);
                    }
                    return msEncrypt.ToArray();
                }
            }
        }

        // Desencripta un array de bytes a una cadena de texto usando AES.
        private string Decrypt(byte[] cipherText)
        {
            using (Aes aesAlg = Aes.Create())
            {
                aesAlg.Key = _key;
                aesAlg.IV = _iv;

                ICryptoTransform decryptor = aesAlg.CreateDecryptor(aesAlg.Key, aesAlg.IV);
                using (MemoryStream msDecrypt = new MemoryStream(cipherText))
                using (CryptoStream csDecrypt = new CryptoStream(msDecrypt, decryptor, CryptoStreamMode.Read))
                using (StreamReader srDecrypt = new StreamReader(csDecrypt))
                {
                    return srDecrypt.ReadToEnd();
                }
            }
        }

    }
}
