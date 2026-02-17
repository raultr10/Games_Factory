create database Games_Factory
GO
USE [Games_Factory]
GO
/****** Object:  UserDefinedFunction [dbo].[HashSHA256]    Script Date: 22/01/2026 18:27:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[HashSHA256](@input NVARCHAR(MAX))
RETURNS VARCHAR(44)
AS
BEGIN
    DECLARE @hash VARBINARY(32);
    SET @hash = HASHBYTES('SHA2_256', @input);
    RETURN CONVERT(VARCHAR(44), @hash, 2);
END;
GO
/****** Object:  Table [dbo].[Categoria_Empleado]    Script Date: 22/01/2026 18:27:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categoria_Empleado](
	[ID_emp] [char](11) NOT NULL,
	[tipo_empleado] [varchar](15) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_emp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Empleado]    Script Date: 22/01/2026 18:27:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Empleado](
	[ID_emp] [char](11) NOT NULL,
	[nombre_emp] [varchar](30) NOT NULL,
	[apellidos_emp] [varchar](50) NOT NULL,
	[direccion] [varchar](55) NOT NULL,
	[fecha_naci] [date] NOT NULL,
	[telefono] [char](9) NOT NULL,
	[codigo_postal] [char](5) NOT NULL,
	[correo_emp] [varchar](55) NOT NULL,
	[contrasena_emp] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK__Empleado__254D3A1576DE4182] PRIMARY KEY CLUSTERED 
(
	[ID_emp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Noticia]    Script Date: 22/01/2026 18:27:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Noticia](
	[ID_noticia] [char](12) NOT NULL,
	[titulo] [varchar](60) NOT NULL,
	[descripcion] [varchar](380) NOT NULL,
	[historia] [varchar](680) NOT NULL,
	[fecha_creacion] [date] NOT NULL,
	[categoria_noticia] [varchar](11) NOT NULL,
	[imagen] [varchar](90) NOT NULL,
 CONSTRAINT [PK__Noticia__0AC8EE29AF609FED] PRIMARY KEY CLUSTERED 
(
	[ID_noticia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Producto]    Script Date: 22/01/2026 18:27:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Producto](
	[ID_producto] [char](12) NOT NULL,
	[descripcion] [varchar](900) NOT NULL,
	[nombre_prod] [varchar](90) NOT NULL,
	[precio] [decimal](5, 2) NOT NULL,
	[anyo] [int] NOT NULL,
	[imagen] [varchar](90) NOT NULL,
 CONSTRAINT [PK__Producto__13C163945D5A84B4] PRIMARY KEY CLUSTERED 
(
	[ID_producto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 22/01/2026 18:27:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuario](
	[ID_DNI] [char](11) NOT NULL,
	[nombre_usu] [varchar](30) NOT NULL,
	[apellidos_usu] [varchar](50) NOT NULL,
	[direccion] [varchar](55) NOT NULL,
	[fecha_naci] [date] NOT NULL,
	[telefono] [char](9) NOT NULL,
	[codigo_postal] [char](5) NOT NULL,
	[correo_usu] [varchar](55) NOT NULL,
	[contrasena_usu] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK__Usuario__2BBF7AEF91FB597E] PRIMARY KEY CLUSTERED 
(
	[ID_DNI] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuario_Producto]    Script Date: 22/01/2026 18:27:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuario_Producto](
	[ID_DNI] [char](11) NOT NULL,
	[cantidad] [int] NOT NULL,
	[total_precio] [decimal](5, 2) NOT NULL,
	[ID_producto] [char](12) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Videojuego]    Script Date: 22/01/2026 18:27:57 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Videojuego](
	[ID_producto] [char](12) NOT NULL,
	[categoria_videojuego] [varchar](10) NOT NULL,
	[tipo_consola] [varchar](11) NOT NULL,
	[idioma] [char](2) NOT NULL,
	[compania] [varchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_producto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Categoria_Empleado] ([ID_emp], [tipo_empleado]) VALUES (N'29.931.573A', N'Empleado_Admin')
INSERT [dbo].[Categoria_Empleado] ([ID_emp], [tipo_empleado]) VALUES (N'32.132.213B', N'Empleado_Normal')
GO
INSERT [dbo].[Empleado] ([ID_emp], [nombre_emp], [apellidos_emp], [direccion], [fecha_naci], [telefono], [codigo_postal], [correo_emp], [contrasena_emp]) VALUES (N'29.931.573A', N'Frank', N'Sirvent Climent', N'calle traves nº1', CAST(N'2000-10-29' AS Date), N'432467832', N'03100', N'frank@gmail.com', N'1BE2E452B46D7A0D9656BBB1F768E8248EBA1B75BAED65F5D99EAFA948899A6A')
INSERT [dbo].[Empleado] ([ID_emp], [nombre_emp], [apellidos_emp], [direccion], [fecha_naci], [telefono], [codigo_postal], [correo_emp], [contrasena_emp]) VALUES (N'32.132.213B', N'Jorge', N'Cremades', N'calle Fin', CAST(N'2004-08-20' AS Date), N'364559761', N'04600', N'empresa@gmail.com', N'38083C7EE9121E17401883566A148AA5C2E2D55DC53BC4A94A026517DBFF3C6B')
GO
INSERT [dbo].[Noticia] ([ID_noticia], [titulo], [descripcion], [historia], [fecha_creacion], [categoria_noticia], [imagen]) VALUES (N'K32.564.924Q', N'La censura de Martha is Dead en PS5 y PS4 al detalle:', N'El videojuego de terror psicológico llega a las tiendas con un modo opcional para censurar escenas.', N'Luca Dalcò, de LKA, profundiza en una entrevista con IGN las modificaciones realizadas, garantizando que afectan a una parte muy pequeña del juego. "Los cambios que se han hecho no distorsionan la experiencia de juego", explica. Pese a ello la censura existe, y dejan sin interacción un par de escenas. Antes de continuar, os advertimos que las descripción son bastante gráficas y puede ser spoilers.', CAST(N'2023-04-04' AS Date), N'Playstation', N'202011251322375_1.jpg')
GO
INSERT [dbo].[Producto] ([ID_producto], [descripcion], [nombre_prod], [precio], [anyo], [imagen]) VALUES (N'F28.971.452Y', N'Far Cry 6 Yara Edition protagonizado por Giancarlo Expósito de Breaking Bad, como dictador de Yara, una isla basada en Cuba, explora toda la isla y disfrutala con todos los retos y desafíos que contiene esta gran aventura. Con Far Cry 6, sé el dictador y actúa como tal sin que te tiemble el pulso, descubre el increíble universo de Yara con Far Cry 6.

 

Adéntrate en la cruda experiencia de una improvisada guerrilla moderna y derroca a un dictador y a su hijo para liberar Yara. ', N'FAR CRY 6', CAST(50.00 AS Decimal(5, 2)), 2022, N'farcry6.jpg')
INSERT [dbo].[Producto] ([ID_producto], [descripcion], [nombre_prod], [precio], [anyo], [imagen]) VALUES (N'F45.283.183G', N'¡Acompaña a Mario en un nuevo viaje inolvidable!
Compra y ajústate la gorra y emprende un viaje único con Super Mario Odyssey para Nintendo Switch. El famoso fontanero vuelve a las plataformas 3D acompañado por un nuevo aliado muy poco convencional.

Acompaña a Mario en una aventura en 3D enorme por todo el planeta usando sus nuevas habilidades para recoger lunas que servirán de combustible a tu aeronave, la Odyssey. ¡Y entretanto, rescata a la princesa Peach de las garras de Bowser!

Esta aventura 3D de Mario de estilo "sandbox" —la primera desde Super Mario 64 en 1997 y Super Mario Sunshine para Game Cube en 2002— está llena a reventar de secretos y sorpresas. Y con los nuevos movimientos de Mario, como lanzamiento de sombrero, Salto Sombrero o captura, vivirás experiencias de juego como no has conocido nunca en un juego de Mario. ¡Prepárate para viajar a tierras extrañas más allá', N'SUPER MARIO ODYSSEY', CAST(55.99 AS Decimal(5, 2)), 2018, N'super-mario-odyssey.jpg')
INSERT [dbo].[Producto] ([ID_producto], [descripcion], [nombre_prod], [precio], [anyo], [imagen]) VALUES (N'Q23.153.156A', N'Ábrete camino a través de una aventura interdimensional.
Salta de dimensión con Ratchet y Clank mientras se enfrentan a un malvado emperador de otra realidad. Salta entre mundos llenos de acción y más allá a velocidades alucinantes, con imágenes deslumbrantes y un arsenal loco, mientras los aventureros intergalácticos llegan a la consola PS5.

¿A que esperas para continuar la aventura? ', N'RATCHET & CLANK: UNA DIMENSIÓN APARTE', CAST(35.22 AS Decimal(5, 2)), 2021, N'Ratchet.jpg')
INSERT [dbo].[Producto] ([ID_producto], [descripcion], [nombre_prod], [precio], [anyo], [imagen]) VALUES (N'W45.390.138S', N'La Tierra ya no nos pertenece
Repudiada por su tribu al nacer, Aloy ha pasado toda su vida buscando respuestas a antiguos secretos y preguntas prohibidas. En una época en la que la humanidad ya no es la especie dominante y seres mecánicos con forma animal nos han arrebatado el control, la joven cazadora se embarcará en un viaje a través de un gigantesco mundo abierto post-apocalíptico para descubrir por qué la Tierra ya no nos pertenece. Horizon Zero Dawn, el nuevo y ambicioso proyecto de los veteranos Guerrilla, llega a PlayStation 4 retrocediendo la evolución humana hasta una nueva era de tribus de cazadores y recolectores en un juego de acción y rol con intensos combates estratégicos en los que ninguna especie de animal robótico se comportará igual que otra.', N'HORIZON: ZERO DAWN', CAST(35.90 AS Decimal(5, 2)), 2019, N'horizon-zero-dawn.jpg')
GO
INSERT [dbo].[Usuario] ([ID_DNI], [nombre_usu], [apellidos_usu], [direccion], [fecha_naci], [telefono], [codigo_postal], [correo_usu], [contrasena_usu]) VALUES (N'12.345.912A', N'adssad', N'asdas', N'dads', CAST(N'2023-06-01' AS Date), N'123456789', N'02132', N'd@gmail.com', N'GKw+c0PwFokMUQ6T+TUmEWnZ4/VlQ2Qpgw+vCTT0+OQ=')
INSERT [dbo].[Usuario] ([ID_DNI], [nombre_usu], [apellidos_usu], [direccion], [fecha_naci], [telefono], [codigo_postal], [correo_usu], [contrasena_usu]) VALUES (N'12.465.891A', N'Josep', N'Decimo', N'Calle Prometheo', CAST(N'2023-03-27' AS Date), N'123456472', N'21304', N'josep@gmail.com', N'LqMxSsRxeIAwzMvki4N19ueatfMgPSNo1C8dVDX7rcY=')
INSERT [dbo].[Usuario] ([ID_DNI], [nombre_usu], [apellidos_usu], [direccion], [fecha_naci], [telefono], [codigo_postal], [correo_usu], [contrasena_usu]) VALUES (N'34.268.123F', N'Juani', N'Compost', N'Calle Belmont', CAST(N'2023-03-30' AS Date), N'560153485', N'03240', N'juani@gmail.com', N's3XjruSwhp/e6ef7jzmXXxzgwigg2eh8gGdsdPaGIug=')
INSERT [dbo].[Usuario] ([ID_DNI], [nombre_usu], [apellidos_usu], [direccion], [fecha_naci], [telefono], [codigo_postal], [correo_usu], [contrasena_usu]) VALUES (N'45.787.878D', N'frank', N'sir', N'dsadsd', CAST(N'2025-11-12' AS Date), N'654654655', N'03100', N'frank@gmail.com', N'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=')
GO
INSERT [dbo].[Usuario_Producto] ([ID_DNI], [cantidad], [total_precio], [ID_producto]) VALUES (N'12.465.891A', 2, CAST(100.00 AS Decimal(5, 2)), N'F28.971.452Y')
INSERT [dbo].[Usuario_Producto] ([ID_DNI], [cantidad], [total_precio], [ID_producto]) VALUES (N'12.465.891A', 2, CAST(71.80 AS Decimal(5, 2)), N'W45.390.138S')
INSERT [dbo].[Usuario_Producto] ([ID_DNI], [cantidad], [total_precio], [ID_producto]) VALUES (N'34.268.123F', 2, CAST(111.98 AS Decimal(5, 2)), N'F45.283.183G')
INSERT [dbo].[Usuario_Producto] ([ID_DNI], [cantidad], [total_precio], [ID_producto]) VALUES (N'45.787.878D', 2, CAST(100.00 AS Decimal(5, 2)), N'F28.971.452Y')
GO
INSERT [dbo].[Videojuego] ([ID_producto], [categoria_videojuego], [tipo_consola], [idioma], [compania]) VALUES (N'F28.971.452Y', N'Plataforma', N'Nintendo', N'IN', N'Ubisoft')
INSERT [dbo].[Videojuego] ([ID_producto], [categoria_videojuego], [tipo_consola], [idioma], [compania]) VALUES (N'F45.283.183G', N'Plataforma', N'Nintendo', N'SP', N'Nintendo')
INSERT [dbo].[Videojuego] ([ID_producto], [categoria_videojuego], [tipo_consola], [idioma], [compania]) VALUES (N'Q23.153.156A', N'Plataforma', N'PC', N'IN', N'Sony')
INSERT [dbo].[Videojuego] ([ID_producto], [categoria_videojuego], [tipo_consola], [idioma], [compania]) VALUES (N'W45.390.138S', N'Acción', N'Playstation', N'SP', N'Sony')
GO
ALTER TABLE [dbo].[Categoria_Empleado]  WITH CHECK ADD  CONSTRAINT [FK__Categoria__ID_em__61F08603] FOREIGN KEY([ID_emp])
REFERENCES [dbo].[Empleado] ([ID_emp])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Categoria_Empleado] CHECK CONSTRAINT [FK__Categoria__ID_em__61F08603]
GO
ALTER TABLE [dbo].[Usuario_Producto]  WITH CHECK ADD  CONSTRAINT [FK__Usuario_P__ID_DN__45544755] FOREIGN KEY([ID_DNI])
REFERENCES [dbo].[Usuario] ([ID_DNI])
GO
ALTER TABLE [dbo].[Usuario_Producto] CHECK CONSTRAINT [FK__Usuario_P__ID_DN__45544755]
GO
ALTER TABLE [dbo].[Usuario_Producto]  WITH CHECK ADD  CONSTRAINT [FK__Usuario_P__ID_pr__46486B8E] FOREIGN KEY([ID_producto])
REFERENCES [dbo].[Producto] ([ID_producto])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Usuario_Producto] CHECK CONSTRAINT [FK__Usuario_P__ID_pr__46486B8E]
GO
ALTER TABLE [dbo].[Videojuego]  WITH CHECK ADD  CONSTRAINT [FK__Videojueg__ID_pr__436BFEE3] FOREIGN KEY([ID_producto])
REFERENCES [dbo].[Producto] ([ID_producto])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Videojuego] CHECK CONSTRAINT [FK__Videojueg__ID_pr__436BFEE3]
GO
ALTER TABLE [dbo].[Categoria_Empleado]  WITH CHECK ADD CHECK  (([tipo_empleado] like 'Empleado_Normal' OR [tipo_empleado] like 'Empleado_Admin'))
GO
ALTER TABLE [dbo].[Empleado]  WITH CHECK ADD  CONSTRAINT [CK__Empleado__codigo__3AD6B8E2] CHECK  (([codigo_postal] like '[0-9][0-9][0-9][0-9][0-9]'))
GO
ALTER TABLE [dbo].[Empleado] CHECK CONSTRAINT [CK__Empleado__codigo__3AD6B8E2]
GO
ALTER TABLE [dbo].[Empleado]  WITH CHECK ADD  CONSTRAINT [CK__Empleado__ID_emp__38EE7070] CHECK  (([ID_emp] like '[0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9][A-Z]'))
GO
ALTER TABLE [dbo].[Empleado] CHECK CONSTRAINT [CK__Empleado__ID_emp__38EE7070]
GO
ALTER TABLE [dbo].[Empleado]  WITH CHECK ADD  CONSTRAINT [CK__Empleado__telefo__39E294A9] CHECK  (([telefono] like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'))
GO
ALTER TABLE [dbo].[Empleado] CHECK CONSTRAINT [CK__Empleado__telefo__39E294A9]
GO
ALTER TABLE [dbo].[Noticia]  WITH CHECK ADD  CONSTRAINT [CK__Noticia__categor__4A18FC72] CHECK  (([categoria_noticia]='Playstation' OR [categoria_noticia]='Nintendo' OR [categoria_noticia]='Xbox' OR [categoria_noticia]='PC'))
GO
ALTER TABLE [dbo].[Noticia] CHECK CONSTRAINT [CK__Noticia__categor__4A18FC72]
GO
ALTER TABLE [dbo].[Noticia]  WITH CHECK ADD  CONSTRAINT [CK__Noticia__ID_noti__4924D839] CHECK  (([ID_Noticia] like '[A-Z][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9][A-Z]'))
GO
ALTER TABLE [dbo].[Noticia] CHECK CONSTRAINT [CK__Noticia__ID_noti__4924D839]
GO
ALTER TABLE [dbo].[Producto]  WITH CHECK ADD  CONSTRAINT [CK__Producto__ID_pro__3DB3258D] CHECK  (([ID_producto] like '[A-Z][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9][A-Z]'))
GO
ALTER TABLE [dbo].[Producto] CHECK CONSTRAINT [CK__Producto__ID_pro__3DB3258D]
GO
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [CK__Usuario__codigo___361203C5] CHECK  (([codigo_postal] like '[0-9][0-9][0-9][0-9][0-9]'))
GO
ALTER TABLE [dbo].[Usuario] CHECK CONSTRAINT [CK__Usuario__codigo___361203C5]
GO
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [CK__Usuario__ID_DNI__3429BB53] CHECK  (([ID_DNI] like '[0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9][A-Z]'))
GO
ALTER TABLE [dbo].[Usuario] CHECK CONSTRAINT [CK__Usuario__ID_DNI__3429BB53]
GO
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD  CONSTRAINT [CK__Usuario__telefon__351DDF8C] CHECK  (([telefono] like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'))
GO
ALTER TABLE [dbo].[Usuario] CHECK CONSTRAINT [CK__Usuario__telefon__351DDF8C]
GO
ALTER TABLE [dbo].[Videojuego]  WITH CHECK ADD CHECK  (([categoria_videojuego]='Plataforma' OR [categoria_videojuego]='Acción' OR [categoria_videojuego]='Terror' OR [categoria_videojuego]='Aventura'))
GO
ALTER TABLE [dbo].[Videojuego]  WITH CHECK ADD CHECK  (([idioma]='IN' OR [idioma]='SP' OR [idioma]='JP'))
GO
ALTER TABLE [dbo].[Videojuego]  WITH CHECK ADD CHECK  (([tipo_consola]='Nintendo' OR [tipo_consola]='Xbox' OR [tipo_consola]='Playstation' OR [tipo_consola]='PC'))
GO
/****** Object:  StoredProcedure [dbo].[BuscarNoticias]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[BuscarNoticias]
(@titulo varchar(60))
as
begin

select *
from Noticia
where Noticia.titulo like '%'+@titulo+'%'

end
GO
/****** Object:  StoredProcedure [dbo].[BuscarProducto]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[BuscarProducto]
(@nombre_prod varchar(45))
as
begin

select *
from Producto, Videojuego
where Producto.ID_producto = Videojuego.ID_producto
and Producto.nombre_prod like '%'+@nombre_prod+'%'

end
GO
/****** Object:  StoredProcedure [dbo].[EditarNoticia]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   proc [dbo].[EditarNoticia]
(@ID_noticia char(12), @titulo varchar(60), @descripcion varchar(380), @historia varchar(680), @fecha_creacion date, @categoria_noticia varchar(11), @imagen varchar(90))
as

update Noticia set titulo = @titulo, descripcion = @descripcion, historia = @historia, fecha_creacion = @fecha_creacion, categoria_noticia = @categoria_noticia, imagen = @imagen where Noticia.ID_noticia = @ID_noticia

GO
/****** Object:  StoredProcedure [dbo].[EditarVideojuego]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   proc [dbo].[EditarVideojuego]
(@ID_producto char(12), @descripcion varchar(900), @nombre_prod varchar(90), @precio decimal(5,2), @anyo int, @imagen varchar(90),
@categoria_videojuego varchar(10), @tipo_consola varchar(11), @idioma char(2), @compania varchar(30))
as
update Videojuego set categoria_videojuego = @categoria_videojuego, tipo_consola = @tipo_consola, idioma = @idioma, compania = @compania where Videojuego.ID_producto = @ID_producto
update Producto set descripcion = @descripcion, nombre_prod = @nombre_prod, precio = @precio, anyo = @anyo, imagen = @imagen where Producto.ID_producto = @ID_producto
GO
/****** Object:  StoredProcedure [dbo].[EliminarNoticias]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   proc [dbo].[EliminarNoticias]
(@ID_noticia char(12))
as
delete from Noticia where Noticia.ID_noticia = @ID_noticia
GO
/****** Object:  StoredProcedure [dbo].[EliminarVideojuegos]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   proc [dbo].[EliminarVideojuegos]
(@ID_producto char(12))
as
delete from Videojuego where Videojuego.ID_producto = @ID_producto
delete from Producto where Producto.ID_producto = @ID_producto

GO
/****** Object:  StoredProcedure [dbo].[FiltrarCategoriaV]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[FiltrarCategoriaV]
(@categoria_videojuego varchar(10))
as
begin

select *
from Producto, Videojuego
where Producto.ID_producto = Videojuego.ID_producto
and Videojuego.categoria_videojuego = 'Acción'

end
GO
/****** Object:  StoredProcedure [dbo].[InsertarCompraUsuario]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   procedure [dbo].[InsertarCompraUsuario]
(@ID_DNI char(11), @ID_producto char(12), @cantidad int, @total_precio decimal(5,2))
as

insert into Usuario_Producto
values
(@ID_DNI, @cantidad, @total_precio, @ID_producto)

GO
/****** Object:  StoredProcedure [dbo].[InsertarNoticias]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[InsertarNoticias]
(@ID_noticia char(12), @titulo varchar(60), @descripcion varchar(380), @historia varchar(680), @fecha_creacion date, @categoria_noticia varchar(11), @imagen varchar(90))
as

insert into Noticia
values
(@ID_noticia , @titulo , @descripcion , @historia, @fecha_creacion, @categoria_noticia, @imagen )

GO
/****** Object:  StoredProcedure [dbo].[InsertarUsuarios]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[InsertarUsuarios]
(@ID_DNI char(11), @nombre_usu varchar(30), @apellidos_usu varchar(50), @direccion varchar(55), @fecha_naci date, @telefono char(9), @codigo_postal char(5), @correo_usu varchar(55), @contrasena_usu nvarchar(150))
as

insert into Usuario
values
(@ID_DNI, @nombre_usu, @apellidos_usu, @direccion, @fecha_naci, @telefono, @codigo_postal, @correo_usu, @contrasena_usu)

GO
/****** Object:  StoredProcedure [dbo].[InsertarVideojuegos]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[InsertarVideojuegos]
(@ID_producto char(12), @descripcion varchar(900), @nombre_prod varchar(90), @precio decimal(5,2), @anyo int, @imagen varchar(90),
@categoria_videojuego varchar(10), @tipo_consola varchar(11), @idioma char(2), @compania varchar(30))
as

insert into Producto
values
(@ID_producto,@descripcion,@nombre_prod, @precio,@anyo,@imagen)

insert into Videojuego
values
(@ID_producto,@categoria_videojuego,@tipo_consola, @idioma, @compania)


GO
/****** Object:  StoredProcedure [dbo].[MostrarEmpleados]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[MostrarEmpleados]
as
begin

	select *
	from Empleado, Categoria_Empleado
	where Empleado.ID_emp = Categoria_Empleado.ID_emp

end
GO
/****** Object:  StoredProcedure [dbo].[MostrarNoticias]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[MostrarNoticias]
as
select * 
from Noticia
GO
/****** Object:  StoredProcedure [dbo].[MostrarProductos]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[MostrarProductos]
as
select *
from Producto
GO
/****** Object:  StoredProcedure [dbo].[MostrarUsuarios]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[MostrarUsuarios]
as
begin

	select *
	from Usuario

end
GO
/****** Object:  StoredProcedure [dbo].[MostrarVideojuegos]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[MostrarVideojuegos]
as
select * 
from Producto, Videojuego
where Producto.ID_producto = Videojuego.ID_producto
GO
/****** Object:  StoredProcedure [dbo].[verificarEmpleados]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[verificarEmpleados]
(@correo_emp varchar(55), @contrasena_emp varchar(35), @verificar bit)
as
begin

declare Empleado cursor

	for select Empleado.ID_emp
	from Empleado
	
	open Empleado
	declare @ID_emp char(11)

	fetch next from Empleado into @ID_emp

	while @@FETCH_STATUS = 0
		begin

			declare @correoVerificar varchar(55) = ''
			declare @contrasena_EmpVerificar varchar(35) = ''
			declare @tipo_EmpVerificar varchar(15) = ''

			select @tipo_EmpVerificar = Categoria_Empleado.tipo_empleado
			from Empleado, Categoria_Empleado
			where Empleado.ID_emp = Categoria_Empleado.ID_emp
			and Empleado.ID_emp = @ID_emp
			

			select @correoVerificar = Empleado.correo_emp
			from Empleado
			where Empleado.ID_emp = @ID_emp

			select @contrasena_EmpVerificar = Empleado.contrasena_emp
			from Empleado
			where Empleado.ID_emp = @ID_emp

				if @correo_emp = @correoVerificar and @contrasena_emp = @contrasena_EmpVerificar
					begin

						set @verificar = 1 

						if @tipo_EmpVerificar = 'Empleado_Admin'
							begin

							set @verificar = 1 

							end
						else
							begin

							set @verificar = 0 

							end

					end


			fetch next from Empleado into @ID_emp

		end

		close Empleado
		deallocate Empleado


end
GO
/****** Object:  StoredProcedure [dbo].[verificarUsuarios]    Script Date: 22/01/2026 18:27:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   procedure [dbo].[verificarUsuarios]
(@correo_usu varchar(55), @contrasena_usu varchar(35), @verificar bit)
as
begin

declare Usuarios cursor

	for select Usuario.ID_DNI
	from Usuario
	
	open Usuarios
	declare @ID_DNI char(11)

	fetch next from Usuarios into @ID_DNI

	while @@FETCH_STATUS = 0
		begin

			declare @correoVerificar varchar(55) = ''
			declare @contrasena_usuVerificar varchar(35) = ''

			select @correoVerificar = Usuario.correo_usu
			from Usuario
			where Usuario.ID_DNI = @ID_DNI

			select @contrasena_usuVerificar = Usuario.contrasena_usu
			from Usuario
			where Usuario.ID_DNI = @ID_DNI

				if @correo_usu = @correoVerificar and @contrasena_usu = @contrasena_usuVerificar
					begin

						set @verificar = 1 

					end


			fetch next from Usuarios into @ID_DNI

		end

		close Usuarios
		deallocate Usuarios


end
GO
