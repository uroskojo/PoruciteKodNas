
webNarucivanje.controller('regFormCtrl', function($scope, $location, regFactory){
	
	$scope.registruj = function(korisnik)
	{
		regFactory.registruj(korisnik).success(function(data){
			
			$scope.porukaReg = data;
			location.reload();
		});
	}
	var i = 0;
	$scope.prikaziFormu = function()
	{
		if(++i == 2)
		{
			$scope.prikazi = false;
			i = 0;
		}else{	
			$scope.prikazi = true;
			
		}
	}
	
});


webNarucivanje.controller('korisniciCtrl', function($scope, korisniciFactory){
	
	prikazKorisnika();
	function prikazKorisnika(){
		
		korisniciFactory.korisniciKartica().success(function(data){
			$scope.korisnici = data;
					
		});
	}
	
	$scope.save = function(k){//cuva promenu uloge
		
		korisniciFactory.save(k).success(function(data){
			
			alert("Uspesno je promenjena uloga korisniku " + k.korisnickoIme + ".");
		});
	}

});

webNarucivanje.controller('artikalCtrl', function($scope,artikliFactory, restoraniFactory){
	
	$scope.btnDodajArtikal = true;
	prikazArtikala();
	prikazRestorana();
	
	function prikazRestorana(){
		var i;
		var sviSuObrisani = true;//ako postoje, ali su svi logicki obrisani ne prikazuj zaglavlje
		
		restoraniFactory.getRestaurants().success(function(data){
			$scope.restorani = data;
	
			if(data == null)
				$scope.imaRestorana = false;
			else{
				
				for(i=0; i<$scope.restorani.length; i++)
				{
					if(!($scope.restorani[i].obrisan))
					{
						sviSuObrisani = false;
						break;
					}	
				}
				if(sviSuObrisani)
					$scope.imaRestorana = false;
				else
					$scope.imaRestorana = true;
			}
				
		});
	}
	
	function resetForm(){
		
		document.getElementById('nazivA').value = null;
		document.getElementById('jedinicnaCenaA').value = null;
		document.getElementById('opisA').value = null;
		document.getElementById('kolicinaA').value= null;
		document.getElementById('tipA').value = "HRANA";
		
	}
	
	$scope.dodajArtikal = function(){
	
		var a={};
		a.naziv = document.getElementById('nazivA').value;	
		a.jedinicnaCena = document.getElementById('jedinicnaCenaA').value;
		a.opis = document.getElementById('opisA').value;
		a.kolicina = document.getElementById('kolicinaA').value;
		a.tip = document.getElementById('tipA').value;
		a.restoran = document.getElementById('restoraniID').value;
		
		artikliFactory.dodajArtikal(a).success(function(data){
			prikazArtikala();
			
		});
	
		resetForm();
		
	}

	
	$scope.izmeniArtikal = function(idx){
		
		$scope.btnIzmenaArtikla = true;
		$scope.btnDodajArtikal = false;
		$scope.a = $scope.artikli[idx];
	
		
		document.getElementById('nazivA').value = $scope.a.naziv;	
		document.getElementById('jedinicnaCenaA').value = $scope.a.jedinicnaCena;
		document.getElementById('opisA').value = $scope.a.opis;
		document.getElementById('kolicinaA').value= $scope.a.kolicina;
		document.getElementById('tipA').value= $scope.a.tip;
		
	}
	
	$scope.sacuvajIzmeneArtikla = function(){
		
		$scope.btnIzmenaArtikla = false;
		$scope.btnDodajArtikal = true;

		
		$scope.a.naziv = document.getElementById('nazivA').value;	
		$scope.a.jedinicnaCena = document.getElementById('jedinicnaCenaA').value;
		$scope.a.opis = document.getElementById('opisA').value;
		$scope.a.kolicina = document.getElementById('kolicinaA').value;
		$scope.a.tip = document.getElementById('tipA').value;
			
		
		artikliFactory.sacuvajIzmeneArtikla($scope.a).success(function(data){	
			prikazArtikala();
			
			if(data != "Vec postoji artikal s tim nazivom")
				resetForm();
			
		});
		
	}
	$scope.obrisiArtikal = function(idx){
		
		var artikalZaBrisanje = $scope.artikli[idx];
		$scope.artikli.splice(idx, 1);
		
		artikliFactory.obrisiArtikal(artikalZaBrisanje).success(function(data){
			prikazArtikala();
			
			
		});
	}
	
	function prikazArtikala(){

		var sviSuObrisani = true;
		
		artikliFactory.getArticles().success(function(data){

			$scope.artikli = data;	
			
			if(data == null)
				$scope.imaArtikala = false;
			else{
				
				for(i=0; i<$scope.artikli.length; i++)
				{
					if(!($scope.artikli[i].obrisan))
					{
						sviSuObrisani = false;
						break;
					}	
				}
				if(sviSuObrisani)
					$scope.imaArtikala = false;
				else
					$scope.imaArtikala = true;
			}
			
			
		});
	}	
	
});


webNarucivanje.controller('vozilaCtrl', function($scope,vozilaFactory, korisniciFactory){
	
	$scope.btnDodajVozilo = true;
	prikazVozila();
	
	function initLinks()
	{
		vozilaFactory.getVehicles().success(function(data){
			
			$scope.vozila = data;
			var i;
			for(i=0; i < $scope.vozila.length; i++)
			{
				
				if($scope.vozila[i].dostavljac != "/")
					$scope.vozila[i].imaDostavljaca = true;
				else
					$scope.vozila[i].imaDostavljaca = false;
			}
		});
		
	
	}
	
	initLinks();
	
	$scope.uzmiVozilo = function()
	{
		var vozilaSelect = document.getElementsByName('Vozila');
		var izabranoVozilo;
		var vozilo={};
		
		for(i=0; i < vozilaSelect.length; i++)
		{
			if(vozilaSelect[i].checked)
			{
				izabranoVozilo = vozilaSelect[i].value;
				break;
			}
		}
		vozilo.id = izabranoVozilo;
		vozilaFactory.izborVozilaDostavljac(vozilo).success(function(data){
			
			alert(data);
			location.reload();
		});
		
	}
	
	$scope.ukloniDostavljaca = function ukloniDostavljaca(idx)
	{
		$scope.v = $scope.vozila[idx];
				
		vozilaFactory.ukloniDostavljaca($scope.v).success(function(data){
			
			$scope.v.dostavljac = data.dostavljac;
			$scope.v.imaDostavljaca = false;
			
			
		});
		
	}
	
	
	function prikazDostavljaca()
	{
		korisniciFactory.getDostavljaci().success(function(data){
			
			$scope.dostavljaci = data;
			
		});
	}
	

	var tempVozilo = {};	//pomocni objekat, da bih preneo selektovani auto u modalni dijalog
	
	$scope.prikaziModal = function prikaziModal(idx)
	{
		$scope.v = $scope.vozila[idx];
		tempVozilo = $scope.v;
		prikazDostavljaca();
	}
	
	$scope.dodeliVozilo = function()
	{
		var dostavljaciImena = document.getElementsByName('Dostavljaci');
		var komeSeDajeVozilo;  //dostavljac
		
		for(i=0; i <dostavljaciImena.length; i++)
		{
			if(dostavljaciImena[i].checked)
			{
				komeSeDajeVozilo = dostavljaciImena[i].value;
				break;
			}
				
			else
				console.log("nije cekiran " + dostavljaciImena[i].value);
		}
		var j;
		for(j=0; j < $scope.dostavljaci.length; j++)
		{
				if($scope.dostavljaci[j].korisnickoIme === komeSeDajeVozilo)
				{
					
					$scope.dostavljaci[j].id_vozila = tempVozilo.id; //dodeljujem vozilo dostavljacu
								
					korisniciFactory.sacuvajDodeluVozila($scope.dostavljaci[j]).success(function(data){
						$scope.dostavljaci[j].vozilo.dostavljac =  $scope.dostavljaci[j].korisnickoIme;
						//location.reload();
					});
						
					break;
				}
		}
		
		
	}
	
	
	function resetForm()
	{
		document.getElementById('tipV').value = "AUTOMOBIL";
		document.getElementById('markaV').value = null;
		document.getElementById('modelV').value = null;
		document.getElementById('regOznakaV').value = null;
		document.getElementById('godProizvV').value = null;
		document.getElementById('napomenaV').value = null;
	}
	
	
	$scope.dodajVozilo = function(){

		var v={};
		v.tip = document.getElementById('tipV').value;	
		v.marka = document.getElementById('markaV').value;
		v.model = document.getElementById('modelV').value;
		v.regOznaka = document.getElementById('regOznakaV').value;
		v.godProizv = document.getElementById('godProizvV').value;
		v.napomena = document.getElementById('napomenaV').value;
		
		
		
		vozilaFactory.dodajVozilo(v).success(function(data){
			console.log("poruka sa servisa(vozilo): " + data);
			
			prikazVozila();
			resetForm();
			initLinks();
		});
		
		
	}
	
	$scope.izmeniVozilo = function(idx){
		
		$scope.btnIzmenaVozila = true;
		$scope.btnDodajVozilo = false;
		$scope.v = $scope.vozila[idx];
				
		document.getElementById('tipV').value = $scope.v.tip;
		document.getElementById('markaV').value = $scope.v.marka;
		document.getElementById('modelV').value = $scope.v.model;
		document.getElementById('regOznakaV').value = $scope.v.regOznaka;
		document.getElementById('godProizvV').value = $scope.v.godProizv;
		document.getElementById('napomenaV').value = $scope.v.napomena;
	}
	
	 
	$scope.sacuvajIzmeneVozila = function(){
		
		$scope.btnIzmenaVozila = false;
		$scope.btnDodajVozilo = true;
				
		$scope.v.tip = document.getElementById('tipV').value;
		$scope.v.marka = document.getElementById('markaV').value;
		$scope.v.model = document.getElementById('modelV').value;
		$scope.v.regOznaka = document.getElementById('regOznakaV').value;
		$scope.v.godProizv = document.getElementById('godProizvV').value;
		$scope.v.napomena = document.getElementById('napomenaV').value 
		
		vozilaFactory.sacuvajIzmeneVozila($scope.v).success(function(data){
			
			console.log("vratio u kontroler(vozilo): " + data);
			prikazVozila();
			initLinks();
						
		});
		resetForm();
		
	}

	$scope.obrisiVozilo = function(idx){
		
		var voziloZaBrisanje = $scope.vozila[idx];
		$scope.vozila.splice(idx, 1);
		vozilaFactory.obrisiVozilo(voziloZaBrisanje).success(function(data){
			
			prikazVozila();	
			initLinks();
		});
			
	}
	
	function prikazVozila(){
		
		var i;
		var svaSuObrisana = true;//ako postoje, ali su svi logicki obrisani ne prikazuj zaglavlje
		var dodeljenoJe = {}; 
		
		vozilaFactory.getVehicles().success(function(data){
			
			$scope.vozila = data;
			
			if(data == null)
				$scope.imaVozila = false;
			else{
				
				for(i=0; i<$scope.vozila.length; i++)
				{			
					if(!($scope.vozila[i].obrisan))
					{
						svaSuObrisana = false;
						break;
					}	
				}
				if(svaSuObrisana)
					$scope.imaVozila = false;
				else
					$scope.imaVozila = true;
				
			}
		});
	}
});

webNarucivanje.controller('restoraniCtrl', function($scope, $location, restoraniFactory, artikliFactory, porudzbineFactory){
	
	$scope.btnDodaj = true;	//za prikaz linka 'Dodaj restoran'
	prikazRestorana();
	prikazKategorija();
	$scope.sakrijRestorane = false;
	$scope.prikaziPopularno = true;
	$scope.prikaziSelektRestoran = false;
	popularno();
	
	function popularno(){
		
		artikliFactory.getPopular().success(function(data){
			$scope.popularno = data;
			
		});
	}
	
	function resetForm()
	{
		document.getElementById('nazivR').value = null;	
		document.getElementById('adresaR').value = null;
		document.getElementById('kategR').value = null;
	}
	
	var domacaKuhinja = [];
	var rostilj = [];
	var kineski = [];
	var indijski = [];
	var poslastic = [];
	var picerija = [];
	
	function prikazKategorija()
	{
		restoraniFactory.getRestaurants().success(function(data){
			$scope.restorani = data;
			var i;
			for(i=0; i < $scope.restorani.length; i++)
			{
				if($scope.restorani[i].kateg === "DOMACA_KUHINJA")
					domacaKuhinja.push($scope.restorani[i]);
				else if($scope.restorani[i].kateg === "ROSTILJ")
					rostilj.push($scope.restorani[i]);
				else if($scope.restorani[i].kateg === "KINESKI")
					kineski.push($scope.restorani[i]);
				else if($scope.restorani[i].kateg === "INDIJSKI")
					indijski.push($scope.restorani[i]);
				else if($scope.restorani[i].kateg === "POSLASTICARNICA")
					poslastic.push($scope.restorani[i]);
				else if($scope.restorani[i].kateg === "PICERIJA")
					picerija.push($scope.restorani[i]);
			}
			$scope.domaca = domacaKuhinja;
			$scope.rostilj = rostilj;
			$scope.kineski = kineski;
			$scope.indijski = indijski;
			$scope.poslastic = poslastic;
			$scope.picer = picerija;
		});
	}
	
	function prikazRestorana(){
		var i;
		var sviSuObrisani = true;//ako postoje, ali su svi logicki obrisani ne prikazuj zaglavlje
		
		restoraniFactory.getRestaurants().success(function(data){
			$scope.restorani = data;			
			
			if(data == null)
				$scope.imaRestorana = false;
			else{
				
				for(i=0; i<$scope.restorani.length; i++)
				{
					if(!($scope.restorani[i].obrisan))
					{
						sviSuObrisani = false;
						break;
					}	
				}
				if(sviSuObrisani)
					$scope.imaRestorana = false;
				else
					$scope.imaRestorana = true;
			}
				
		});
	}
	
		
	$scope.prikazSelektRestorana = function(r)
	{
		
		$scope.prikaziPopularno= false;
		$scope.prikaziSelektRestoran = true;

		$scope.r = r;
		$scope.listaPica = r.listaPica;
		$scope.listaJela = r.listaJela;
		
		artikliFactory.getArticlesOfRestaurant(r).success(function(data){
			$scope.sviArtikli = data;	
						
		});	
	
	}
	
	var tempArtikal = {}; //sluzi da prenesem artikal u modal, pa u korpu
	$scope.prikaziModal = function(artikal)
	{
			$scope.selArtikal = artikal;
			$scope.popArt = artikal;
			tempArtikal = artikal;
	}


	$scope.porudzbina = { 
			stavke: [],
			ukupanIznos: 0
	}
	
	$scope.dodajUKorpu = function()
	{
		var stavka = {
				artikal: "",
				kolicina: "",
				cena: "",
				id: ""
		};
		$scope.korpaImaSadrzaj = true;
		var kolicina = document.getElementById('kolicina').value;
		
		stavka.artikal = tempArtikal.naziv;
		stavka.id = tempArtikal.id;
		stavka.cena = tempArtikal.jedinicnaCena;
		
		console.log("tempArtikal: naziv: "+ stavka.artikal + " \ncena: " + stavka.cena);
		stavka.kolicina = kolicina;
		console.log("kolicina stavke(artikla) koju trenutno dodajem: " + stavka.kolicina);
		stavka.cena = parseInt(tempArtikal.jedinicnaCena*kolicina);
		console.log("cena stavke za unetu kolicinu je: " + stavka.cena);

		
		//skip-ujem neku proveru da l vec jeste u korpi
		var sadrzi = false;
						
		for(i=0; i < $scope.porudzbina.stavke.length; i++)
			{
			//	if($scope.porudzbina.stavke[i].id === stavka.id)
				if($scope.porudzbina.stavke[i].id === stavka.artikal)	
					{
						sadrzi = true;
						break;
					}
			}
		
		if(sadrzi === true)
		{
			var novaKol =Number($scope.porudzbina.stavke[i].kolicina) + Number(stavka.kolicina);
			$scope.porudzbina.stavke[i].kolicina = novaKol;
			$scope.porudzbina.stavke[i].cena +=  stavka.cena;
			$scope.porudzbina.ukupanIznos += stavka.cena;
			
		}else
			{
			//$scope.porudzbina.stavke.push(stavka.id);	
			$scope.porudzbina.stavke.push(stavka);
				$scope.porudzbina.ukupanIznos += stavka.cena;
			}
			
		$scope.korpa = $scope.porudzbina.stavke;
		document.getElementById('napomena').value = null;
		document.getElementById('kolicina').value = null;
	}
	
	$scope.naruci = function()
	{
		$scope.korpaImaSadrzaj = false;
		var date = new Date();
		$scope.porudzbina.datumVreme = date.getDate() + "/" + date.getMonth() + "/" + date.getFullYear() + " " + date.getHours() + ":" + date.getMinutes();
		//artikliFactory.poruci(angular.toJson($scope.porudzbina)).success(function(data){
		artikliFactory.poruci($scope.porudzbina).success(function(data){
			$scope.sakrijRestorane = false;
			$scope.artikliRestorana = false;
			$scope.porudzbina.stavke = [];
			$scope.porudzbina.ukupanIznos = 0;
			$scope.korpa = {};
		});		
	}
		
	$scope.pregledArtikala = function(r)
	{
		$scope.artikliRestorana = true;
		$scope.sakrijRestorane = true;
		artikliFactory.getArticlesOfRestaurant(r).success(function(data){
			$scope.restoranArtikli = data;	
			
		});	
	}
	
	$scope.dodajRestoran = function(){
		
		i = 0;
		
		var r={};
		r.naziv = document.getElementById('nazivR').value;	
		r.adresa = document.getElementById('adresaR').value;
		r.kateg = document.getElementById('kategR').value;
		
		restoraniFactory.dodajRestoran(r).success(function(data){
			prikazRestorana();
			resetForm();
			$scope.r = null;
			if(data == null)
			{
				alert('Vec postoji restoran pod imenom ' + data.naziv);
			}
			
		

		});
		
	}
		
	$scope.obrisiRestoran = function(idx){
		
		var restoranZaBrisanje = $scope.restorani[idx];
		console.log("obrisan " + restoranZaBrisanje.listaJela + " i pica: " + restoranZaBrisanje.listaPica);
		 $scope.restorani.splice(idx, 1);
		restoraniFactory.obrisiRestoran(restoranZaBrisanje).success(function(data){
			prikazRestorana();
	
		});
			
	},
	
	$scope.izmeniRestoran = function(idx){
		
		$scope.btnIzmena = true;
		$scope.btnDodaj = false;
		$scope.r = $scope.restorani[idx];
		
		document.getElementById('nazivR').value = $scope.r.naziv;
		document.getElementById('adresaR').value = $scope.r.adresa;
		document.getElementById('kategR').value = $scope.r.kateg;
		
	},
	
	$scope.sacuvajIzmeneRestorana = function(){
		
		
		$scope.btnIzmena = false;
		$scope.btnDodaj = true;
				
		$scope.r.naziv = document.getElementById('nazivR').value;	
		$scope.r.adresa = document.getElementById('adresaR').value;
		$scope.r.kateg = document.getElementById('kategR').value;
	
		restoraniFactory.sacuvajIzmeneRestorana($scope.r).success(function(data){
			prikazRestorana();
		});
		
		document.getElementById('nazivR').value = null;
		document.getElementById('adresaR').value = null;
		document.getElementById('kategR').value = "DOMACA_KUHINJA";
	}
	
});

webNarucivanje.controller("loginCtrl", function($scope, $window, $location, loginFactory, korisniciFactory){
	
	function linkoviTipKorisnika()
	{
		$scope.gost = false;
		$scope.admin = false;
		$scope.dostavljac = false;
		$scope.kupac = false;
		
		korisniciFactory.getTypeOfUser().success(function(data){
						
			if(data.valueOf() == "admin"){
				$scope.admin = true;
			
			}else if(data.valueOf() == "kupac"){
				$scope.kupac = true;
				
			}else if(data.valueOf() == "dostavljac"){
					
				$scope.dostavljac = true;
				
			}else if(data.valueOf() == "gost"){
					
				$scope.gost = true;
			}
			
		
		});
	}
	
	linkoviTipKorisnika();
	
	$scope.linkNaPrijavu = function(){
		
		$('#dodavanjeUKorpuModal').modal('hide');
		$('.modal-backdrop').hide();
		$location.url('/mojNalog');
	}
	
	$scope.login = function(korisnik){
		
		loginFactory.login(korisnik).success(function(data){
					
			if(data.valueOf() == "Pogresno korisnicko ime i/ili lozinka"){
						
					alert(data);
			}else{
				$location.url('/');
			}
			 
			});
	
	}	

	var i = 0;
	$scope.logout = function(){
		
			korisniciFactory.logout().success(function(data){
							
	});	
}
});

webNarucivanje.controller('porudzbineCtrl', function($scope, porudzbineFactory, artikliFactory, korisniciFactory){
	
	$scope.btnDodajPorudzbinu = true;
	prikazPorudzbina();
	prikazDostavljaca();
	prikazKupaca();
	var tempStavka = {};	
	
	var date = new Date();
	function prikazDostavljaca()
	{
		porudzbineFactory.getDostavljaciFormaPorudzbina().success(function(data){
			
			$scope.dostavljaci = data;
		});
		
	}

	function prikazKupaca()
	{
		porudzbineFactory.getKupci().success(function(data){
			
			$scope.kupci = data;
		});
	}
	
	
	$scope.preuzmi = function(p)
	{
		porudzbineFactory.preuzmi(p).success(function(data)
		{
			alert(data);
			prikazPorudzbina();
		});
	}
	
	$scope.dostavi = function(p)
	{
		porudzbineFactory.dostavi(p).success(function(data){
			
			alert(data);
			location.reload();
		});
	}
	
	$scope.prikaziModal = function prikaziModal()
	{
		prikazStavki();
	}
	
	function prikazStavki(){

		artikliFactory.getArticles().success(function(data){
			$scope.stavke = data;	
		});												
	}
	

	
	$scope.potvrdiStavke = function()
	{
		var kolicine = document.getElementsByName("Kolicina");
		var stavkeCheck = document.getElementsByName("Stavke");
		
		var i, j, k;
		var id = [];	//pamti id-jeve izabranih stavki(artikala)
		var tempKolicine = [];
		var cnt = 0;
		
		for(i=0; i < stavkeCheck.length; i++)
		{
				if(stavkeCheck[i].checked)
				{
					id[cnt] = stavkeCheck[i].value;
					tempKolicine[cnt++] = kolicine[i].value;
					console.log("kolicina za selektovani artikal je " + kolicine[i].value);
				}
		}
		i = 0;
		for(j=0; j < $scope.stavke.length; j++)
		{
			for(k=0; k < cnt; k++)
			{
				if($scope.stavke[j].id === id[k])
				{
					tempStavka = $scope.stavke[j];
					tempStavka.kolicina = tempKolicine[k];
					$scope.stavke[i++] = tempStavka; 
				}
			}
		}
		
	}
	
	$scope.dodajPorudzbinuAdmin = function()
	{
		var kupacId = document.getElementById('selectKupci').value;
		var dostavId = document.getElementById('selectDostavljaci').value;
		
	
		var p = {};
		p.stavke = stavke;

		p.datumVreme = date.getDate() + "/" + date.getMonth()+1 + "/" + date.getFullYear() + " " + date.getHours() + ":" + date.getMinutes();
		p.kupac = kupacId;
		p.dostavljac = dostavId;
		p.napomena = document.getElementById('napomena').value;	
		
		
		
		porudzbineFactory.dodajPorudzbinuAdmin(p).success(function(data){
			
			console.log("poruka sa servisa(porudzbina): " + data);
			prikazPorudzbina();
			resetForm();
			
		});
	}
	

	function prikazPorudzbina()
	{
		var i;
		var sveSuObrisane = true;//ako postoje, ali su svi logicki obrisani ne prikazuj zaglavlje
				
		porudzbineFactory.getPorudzbine().success(function(data){
			
			$scope.porudzbine = data;
			
			if(data == null)
				$scope.imaPorudzbina = false;
			else{
				
				for(i=0; i<$scope.porudzbine.length; i++)
				{			
					if(!($scope.porudzbine[i].obrisana))
					{
						sveSuObrisane = false;
						break;
					}	
				}
				if(sveSuObrisane)
					$scope.imaPorudzbina = false;
				else
					$scope.imaPorudzbina = true;
				
			}
		});
	}
	
});








	