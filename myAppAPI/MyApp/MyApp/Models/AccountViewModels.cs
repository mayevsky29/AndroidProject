namespace MyApp.Models
{
    public class RegisterViewModel
    {
        public string Email { get; set; }
        public string FirstName { get; set; }
        public string SecondName { get; set; }
        public string Photo { get; set; }
        public string Phone { get; set; }
        public string Password { get; set; }
        public string ConfirmPassword { get; set; }
    }

    public class UserItemViewModel
    {
        public long Id { get; set; }
        public string Email { get; set; }
        public string FirstName { get; set; }
        public string SecondName { get; set; }
        public string Photo { get; set; }
        public string Phone { get; set; }
    }

    public class LoginViewModel
    {
        /// <summary>
        /// The email of the user
        /// </summary>
        /// <example>petro@gmail.com</example>
        public string Email { get; set; }
        /// <summary>
        /// The password of the user
        /// </summary>
        /// <example>987654321</example>
        public string Password { get; set; }    
    }

    public class TokenResponceViewModel
    {
        /// <summary>
        /// token
        /// </summary>
        /// <example>eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2Mzh9.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI</example>
        public string token { get; set; }
    }
}
