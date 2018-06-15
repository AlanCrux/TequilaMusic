namespace java services
namespace rb services

typedef i32 int 

struct Cancion{
   1 : i32 idCancion,
   2 : string titulo,
   3 : string ruta,
   4 : string idAlbum
}

service servicioCanciones {
	list<Cancion> obtenerCanciones(1:string criterio),
}

