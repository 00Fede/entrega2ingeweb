/**
 * Este modulo se encarga de controlar la interaccion del usuario despues de
 * haberse logueado. Comprende la validacion del rol de usuario Tomar las
 * cookies de la sesion y la navegabilidad dentro de la aplicacion
 * 
 * @author Federico
 */

const URL_HOME = "http://localhost:8080/PruebaWs/rest/";
const URL_USER_SERVICE = "ServicioUsuario/"

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


.controller('navCtrl',function($scope, $cookies, logoutService){
	
	
	$scope.logout = function(){
		var oldSession = $cookies.get("sessionID");
		$cookies.remove("sessionID");
		console.log("logout.. cookie removida..");
		logoutService.logout(oldSession)
				.success(function(data, status, headers, config){
					
							
							console.log("Data recibida = " + data);
							console.log("Registro, status= "+status);
							if(data=="Sesion de usuario "+oldSession+" cerrada correctamente"){
								location.href = "..";
								console.log("location.url result =" + location.url);
							}else{		
								alert(data);
							}
						});
	}
})