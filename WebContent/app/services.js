webNarucivanje.factory('loginFactory', function($http){
	
	var factory={};
	factory.login = function(korisnik){
		
		return $http.post('/PoruciteKodNas/rest/logovanje/login', {
			
			"korisnickoIme":'' + korisnik.username,
			"lozinka": '' + korisnik.password
			
		});
	}
	return factory;
});

webNarucivanje.factory('regFactory', function($http){
	
	var factory = {};
	factory.registruj = function(korisnik){
		
		return $http.post('/PoruciteKodNas/rest/registrovanje',
		{
			"ime" :'' + korisnik.ime,
			"prezime":'' + korisnik.prezime,
			"korisnickoIme":'' + korisnik.korisnickoIme,
			"lozinka": '' + korisnik.lozinka,
			"kontaktTelefon": '' + korisnik.brTel,
			"email" :''+ korisnik.imejl,
			"datumRegistracije":'' + korisnik.datum		
		}		
		);
	};

	return factory;
});

webNarucivanje.factory('korisniciFactory', function($http){
	
	var factory={};
	
	factory.getTypeOfUser = function()
	{
		return $http.get("/PoruciteKodNas/rest/servis/getTypeOfUser")	
	}
	
	factory.getDostavljaci = function()
	{
		return $http.get("/PoruciteKodNas/rest/servis/getDostavljaci")	
	};
	
	
	factory.korisniciKartica = function(){
		
		return $http.get("/PoruciteKodNas/rest/servis/getUsers")	
	};
	
	factory.save = function(k){
		
		return $http.post("/PoruciteKodNas/rest/servis/promenaUloge", {
			
			"ime" :'' + k.ime,
			"prezime":'' + k.prezime,
			"korisnickoIme":'' + k.korisnickoIme,
			"lozinka": '' + k.lozinka,
			"kontaktTelefon": '' + k.brTel,
			"email" :''+ k.imejl,
			"datumRegistracije":'' + k.datum,
			"uloga":'' + k.uloga
			
		})
	};
	
	factory.sacuvajDodeluVozila = function(d)
	{
		return $http.post("/PoruciteKodNas/rest/servis/dodelaVozila", {
			
			"korisnickoIme":'' + d.korisnickoIme,
			"id_vozila":'' + d.id_vozila
			 
		})
	}
	
	factory.logout = function(){
		return $http.post("/PoruciteKodNas/rest/logovanje/logout")
	}
	
	return factory;
});


webNarucivanje.factory('restoraniFactory', function($http){
	
	var factory={};
	factory.dodajRestoran = function(r){
		
		return $http.post("/PoruciteKodNas/rest/servis/addRestaurant", {
			
			"naziv" : '' + r.naziv,
			"adresa": '' + r.adresa,
			"kateg": ''  + r.kateg
			
		})
	};
	
	factory.obrisiRestoran = function(r){
		
		return $http.post("/PoruciteKodNas/rest/servis/deleteRestaurant", {
			"naziv" : '' + r.naziv,
			"id" : '' + r.id,
			"listaPica" : r.listaPica,
			"listaJela" : r.listaJela
		})
	}
	
	factory.getRestaurants = function()
	{
		
		return $http.get("/PoruciteKodNas/rest/servis/getRestaurants");
	}
	
	factory.sacuvajIzmeneRestorana = function(r){
		
		return $http.post("/PoruciteKodNas/rest/servis/saveChangedRestaurant", {
			
			"naziv" : '' + r.naziv,
			"adresa": '' + r.adresa,
			"id": '' + r.id,
			"kateg": r.kateg,
			"listaPica": r.listaPica,
			"listaJela": r.listaJela
		});
		
	}
	
	return factory;
});

webNarucivanje.factory('artikliFactory', function($http){
	
	var factory={};
	
	factory.poruci= function(p)
	{	
		return $http.post("/PoruciteKodNas/rest/servis/narucivanje",{
			
			"stavke": p.stavke,
			"datumVreme": '' + p.datumVreme,
			"status": p.status,
			"napomena": p.napomena,
			"id": p.id
			
		})
	}
	
	
	factory.dodajArtikal = function(a){
				
		return $http.post("/PoruciteKodNas/rest/servis/addArticle", {
						
			"tip": a.tip,
			"naziv": '' + a.naziv,
			"jedinicnaCena": '' + a.jedinicnaCena,
			"opis": '' + a.opis,
			"kolicina": '' + a.kolicina,
			"restoran": a.restoran
			
		});
	}
	factory.getArticlesOfRestaurant = function(r)
	{
		return $http.post("/PoruciteKodNas/rest/servis/getArticlesOfRestaurants", {
			
			"id": '' + r.id
		})
	}
	
	factory.getArticles = function()
	{
		return $http.get("/PoruciteKodNas/rest/servis/getArticles");
	}
		
	
	factory.sacuvajIzmeneArtikla = function(a)
	{
		
		return $http.post("/PoruciteKodNas/rest/servis/saveChangedArticle",{
			
			
			"tip": '' + a.tip,
			"naziv": '' + a.naziv,
			"jedinicnaCena": '' + a.jedinicnaCena,
			"opis": '' + a.opis,
			"kolicina": '' + a.kolicina,
			"restoran": a.restoran,
			"id": '' + a.id
			
		});
	}
	
	factory.obrisiArtikal = function(a){
		
		return $http.post("/PoruciteKodNas/rest/servis/deleteArticle",{
			
			"naziv" : '' + a.naziv,
			"id" : '' + a.id,
			"restoran": '' + a.restoran,
			"tip": '' + a.tip
			
		});
	}
	
	factory.getPopular = function(){
		
		return $http.get("/PoruciteKodNas/rest/servis/popular")
	}
	
	
	return factory;
});

webNarucivanje.factory('vozilaFactory', function($http){
	
	var factory = {};
	
	factory.getVehicles = function(){
		
		return $http.get("/PoruciteKodNas/rest/servis/getVehicles");
	}
	
	factory.dodajVozilo = function(v){
		
		return $http.post("/PoruciteKodNas/rest/servis/addVehicle",{
			
			"tip": '' + v.tip,
			"marka": '' + v.marka,
			"model": '' + v.model,
			"regOznaka":  v.regOznaka,
			"godProizv": '' + v.godProizv,
			"napomena": '' + v.napomena
			
		});
	}
	
	factory.izborVozilaDostavljac = function(v)
	{
		return $http.post("/PoruciteKodNas/rest/servis/izborVozilaDostavljac",
				{
					"id": '' + v.id
				}
		)
	}
	
	factory.sacuvajIzmeneVozila = function(v)
	{
		return $http.post("/PoruciteKodNas/rest/servis/saveChangedVehicle",{
			
			"tip": '' + v.tip,
			"marka": '' + v.marka,
			"model": '' + v.model,
			"regOznaka":  v.regOznaka,
			"godProizv": '' + v.godProizv,
			"napomena": '' + v.napomena,
			"id": '' + v.id,
			"dostavljac": '' + v.dostavljac
			
		});
	}
	factory.obrisiVozilo = function(v){
		
		return $http.post("/PoruciteKodNas/rest/servis/deleteVehicle", {
			"id" : '' + v.id
		})
	}
	
	factory.ukloniDostavljaca = function(v)
	{
		return $http.post("/PoruciteKodNas/rest/servis/uklanjanjeDostavljaca", {
			
			"tip": '' + v.tip,
			"marka": '' + v.marka,
			"model": '' + v.model,
			"regOznaka":  v.regOznaka,
			"godProizv": '' + v.godProizv,
			"napomena": '' + v.napomena,
			"id": '' + v.id,
			"dostavljac": '' + v.dostavljac
			
		})
	}
	
	return factory;
});

webNarucivanje.factory('porudzbineFactory', function($http){
	var factory={};
	
	factory.getPorudzbine = function()
	{
		return $http.get("/PoruciteKodNas/rest/servis/getPorudzbine")
	}
	
	factory.getKupci = function()
	{
		return $http.get("/PoruciteKodNas/rest/servis/getKupci")
	}
	
	factory.preuzmi = function(p)	//dostavljac preuzima porudzbinu
	{
		return $http({
			
			method: 'POST',
			url: '/PoruciteKodNas/rest/servis/preuzimanjePorudzbine',
			data: p
		})
	}
	
	factory.dostavi = function(p)	//dostavljac preuzima porudzbinu
	{
		return $http({
			
			method: 'POST',
			url: '/PoruciteKodNas/rest/servis/dostaviPorudzbinu',
			data: p
		})
	}
	
	factory.dodajPorudzbinuAdmin = function(p)
	{
		return $http.post("/PoruciteKodNas/rest/servis/addPorudzbinaAdmin", {
			
			"stavke": '' + p.stavke,
			"datumVreme": '' + p.datumVreme,
			"kupac": '' + p.kupac,
			"dostavljac": '' + p.dostavljac,
			"napomena": '' + p.napomena,
			"id": '' +p.id
		
		})
	}
	
	factory.getDostavljaciFormaPorudzbina = function()
	{
		return $http.get("/PoruciteKodNas/rest/servis/getDostFormaPorudzbina")
	}
	
	return factory;
});







