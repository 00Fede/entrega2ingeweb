/**
 * App.js Angular modulo para configurar la interaccion entre la vista y el
 * controlador de los requisitos que seran implementados
 * 
 * @author Federico, Andres y Daniel
 */

const URL_HOME = "http://localhost:8080/PruebaWs/rest/";
const URL_USER_SERVICE = "ServicioUsuario/"
const URL_RESERVA_SERVICE = "ServicioReserva/"
	

angular.module('app',['ngRoute','ngCookies'])

/**
 * Servicio que se encarga de ejecutar el servicio web de login
 */
.service('loginService', function($http){
	this.autenticar = function(id, pass, captcha){
		return $http({
			method: "POST",
			url: URL_HOME+URL_USER_SERVICE+"login",
			params:{
				id:id,
				pass:pass,
				captcha:captcha
			}
			
		})
	}
})


.config(['$routeProvider', function($routeProvider) {
	// Cuando este en '/' corre la configuracion del json
	// las rutas se hacen relativas a donde esta el index.html
	$routeProvider.when('/', {
		templateUrl: "pages/login.html",
		controller: "loginCtrl"
	})
}])



/**
 * Este controlador permite controlar la vista del login y le agrega
 * funcionalidad con el backend.
 */
.controller('loginCtrl', function($cookies, $scope, $location, loginService){
	$scope.id = '';
	$scope.password = '';
	$scope.captcha = '';
	
	var cookieId = $cookies.get('sessionID');
	
	
	$scope.autenticar = function() {
		loginService.autenticar($scope.id, $scope.password, $scope.captcha)
						.success(function(data, status, headers, config){
							
							if(data.estado==200){
								// solo si el registro es exitoso
								var today = new Date();
								var expired = new Date(today);
								expired.setMinutes(today.getMinutes() + 30);
								$cookies.put('sessionID',$scope.id,{expires: expired});
								location.href = "pages/main.html";
						
							}else{		
								alert(data.message);
							}
						})
	};
	
})
.controller('mainCtrl',  function($scope, $cookies){
	$scope.idUsuario = $cookies.get('sessionID');
})






