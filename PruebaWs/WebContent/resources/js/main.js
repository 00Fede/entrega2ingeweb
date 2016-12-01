/**
 * Este modulo se encarga de controlar la interaccion del usuario despues de
 * haberse logueado. Comprende la validacion del rol de usuario Tomar las
 * cookies de la sesion y la navegabilidad dentro de la aplicacion
 * 
 * @author Federico
 */

const URL_HOME = "http://localhost:8080/PruebaWs/rest/";
const URL_USER_SERVICE = "ServicioUsuario/";
	const URL_RESERVA_SERVICE = "ServicioReserva/";

angular.module('main',['ngRoute','ngCookies'])

.service("logoutService", function($http){
	this.logout = function(id){
		return $http({
			method: "DELETE",
			url: URL_HOME+URL_USER_SERVICE+"logout",
			params:{
				id:id
			}
		})
	}
})

.service('solicitarReserva', function($http) {
	
	this.solicitarReserv = function(idInv, idDev, fecha, tiempo) {
		return $http({
			method: "POST",
			url: URL_HOME+URL_RESERVA_SERVICE+"crearReserva",
			params:{
				idInv: idInv,
				idDev: idDev,
				fecha: fecha,
				tiempo: tiempo
			}
		});
		
	}
	
	
})


.service('Prestar', function($http){
	this.prestar = function(idReserva){
		return $http({
			method: 'PUT',
			url: 'http://localhost:8080/PruebaWs/rest/ServicioReserva/prestamo',
			params:{
				id : idReserva
			}
		})
	}
})

.service('Listar', function($http){
	this.listarReserva = function(identificador){
		return $http({
			method: 'GET',
			url: 'http://localhost:8080/PruebaWs/rest/ServicioReserva/listarReservas',
			params:{
				id: identificador
				
			}
		})
	}
})


.config(['$routeProvider', function($routeProvider) {
	// Cuando este en '/' corre la configuracion del json
	// las rutas se hacen relativas a donde esta el index.html
	$routeProvider.when('/listarReservas', {
		templateUrl: "listarReserva.html",
		controller: "listarReservaCtrl"
	})
	$routeProvider.when('/solicitarReservas', {
		templateUrl: "solicitarReserva.html",
		controller: "solicitarReservaCtrl"
	})
}])


.controller('navCtrl',function($location, $scope, $cookies, logoutService){
	
	
	$scope.logout = function(){
		var oldSession = $cookies.get("sessionID");
		$cookies.remove("sessionID");
		logoutService.logout(oldSession)
				.success(function(data, status, headers, config){
					
							if(data=="Sesion de usuario "+oldSession+" cerrada correctamente"){
								location.href = "..";
							}else{		
								alert(data);
							}
						});
	}
	
	$scope.irSolicitarReservas = function(){
		$location.url("/solicitarReservas");
	}
	
	$scope.irListarReservas = function(){
		$location.url("/listarReservas");
	}
})

.controller('solicitarReservaCtrl', function($scope, solicitarReserva,$cookies, $location){
	$scope.id = '';
	$scope.idDev = '';
	$scope.fecha = '';
	$scope.tiempo = '';
	
	var cookieId = $cookies.get('sessionID');
	
	$scope.solicitarReserv = function() {
		solicitarReserva.solicitarReserv($scope.id, $scope.idDev, $scope.fecha, $scope.tiempo)
						.success(function(data, status, headers, config){
							alert(data);
						})
						
	};
	
})

.controller('listarReservaCtrl', function($route,$location,$cookies,$scope, Listar,Prestar){ 
	
	$scope.idReserva = "";
	
	var cookieId = $cookies.get('sessionID');
	
	
		Listar.listarReserva(cookieId).then(function successCallback(response){
			if(response.data.reservaWs.length != undefined){
				$scope.listaReserva= response.data.reservaWs;
			}else{
				$scope.listaReserva= response.data;
			}
		}, function errorCallback(response){
			alert("Id invalido");
		
});
	

	
	$scope.prestar=function(value){		
		Prestar.prestar($scope.listaReserva[value].idReserva
				).success(function(data){
					
					if(data == ''){
						alert("El prestamo no se ha podido realizar");
						$scope.prestarId = "";
						
					}else{
						alert(data);
						$route.reload();
					}
				});
}
		
})