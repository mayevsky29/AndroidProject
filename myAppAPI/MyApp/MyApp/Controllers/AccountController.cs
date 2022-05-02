using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MyApp.Data;
using MyApp.Data.Entities.Identity;
using MyApp.Helpers;
using MyApp.Models;
using MyApp.Services;
using System.Drawing.Imaging;

namespace MyApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        private readonly IMapper _mapper;
        private readonly IJwtTokenService _jwtTokenService;
        private readonly UserManager<AppUser> _userManager;
        private readonly AppEFContext _context;
        private readonly ILogger<AccountController> _logger;
        public AccountController(UserManager<AppUser> userManager, IMapper mapper,
            IJwtTokenService jwtTokenService, AppEFContext context, ILogger<AccountController> logger)
        {
            _userManager = userManager;
            _mapper = mapper;
            _jwtTokenService = jwtTokenService;
            _context = context;
            _logger = logger;
        }

        [HttpPost]
        [Route("register")]
        public async Task<IActionResult> Register([FromBody] RegisterViewModel model)
        {
            var img = ImageWorker.FromBase64StringToImage(model.Photo);
            string randomFilename = Path.GetRandomFileName() + ".jpeg";
            var dir = Path.Combine(Directory.GetCurrentDirectory(), "uploads", randomFilename);
            img.Save(dir, ImageFormat.Jpeg);
            var user = _mapper.Map<AppUser>(model);
            user.Photo = randomFilename;
            var result = await _userManager.CreateAsync(user, model.Password);

            if (!result.Succeeded)
                return BadRequest(new { errors = result.Errors });

            return Ok(new { token = _jwtTokenService.CreateToken(user) });
        }

        [HttpGet]
        [Route("users")]
        public async Task<IActionResult> Users()
        {
            // Затримка на отримання фото 2 сек
            Thread.Sleep(2000);
            var list = await _context.Users.Select(x => _mapper.Map<UserItemViewModel>(x))
                .AsQueryable().ToListAsync();

            return Ok(list);
        }

        /// <summary>
        /// Вхід на сайт
        /// </summary>
        /// <param name="model">Модель із даними</param>
        /// <returns>Повертає токен авторизації</returns>
        [HttpPost]
        [Route("login")]
        [ProducesResponseType(StatusCodes.Status200OK, Type = typeof(TokenResponceViewModel))]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<IActionResult> Login([FromBody] LoginViewModel model)
        {
            _logger.LogInformation("Login user");
            // пошук юзера по пошті
            var user = await _userManager.FindByEmailAsync(model.Email);
            if (user != null)
            {
                //throw new AppException("Bad login user");
                if (await _userManager.CheckPasswordAsync(user, model.Password))
                {
                    return Ok(new TokenResponceViewModel { token = _jwtTokenService.CreateToken(user) });
                }
            }
            return BadRequest(new { error = "Користувача не знайдено" });
        }
    }
}
