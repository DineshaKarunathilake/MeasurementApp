angular.module('exampleApp', ['ngRoute', 'ngCookies', 'exampleApp.services','xeditable'])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			$routeProvider.when('/create', {
				templateUrl: 'partials/create.html',
				controller: CreateController
			});
			$routeProvider.when('/selectStage', {
            				templateUrl: 'partials/selectStage.html',
            				controller: SelectStageController
            			});

			$routeProvider.when('/addNewMeasurement/:stage', {
                            templateUrl: 'partials/addNewMeasurement.html',
                            controller: NewMeasurementController
                        });


            $routeProvider.when('/addBody', {
                            templateUrl: 'partials/addBody.html',
                            controller: EditableTableCtrl

                        });

            $routeProvider.when('/addSleeve',
                        {
                             templateUrl: 'partials/addSleeve.html',
                             controller: AddSleeveCtrl

                        });

            $routeProvider.when('/batchList',
                         {
                             templateUrl: 'partials/batchList.html',
                             controller: BatchListCtrl

                         });
            $routeProvider.when('/batchList/:id',
                         {
                             templateUrl: 'partials/viewBatch.html',
                             controller: ViewBatchCtrl

                         });

			$routeProvider.otherwise(
            			{
                            templateUrl: 'partials/firstpage.html',
                        	controller: FirstPageController
                        });


			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		var method = config.method;
			        		var url = config.url;
			      
			        		if (status == 401) {
			        			$location.path( "/firstpage" );
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;

		        		return config || $q.when(config);
		        	}
		        };
		    }
	    );
		   
		} ]
		
	)
	.run(function($rootScope, $location, $cookieStore,	editableOptions) {

	     editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});


		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();


		$rootScope.initialized = true;
	});


//
//function IndexController($scope, BlogPostService) {
//
//	$scope.blogPosts = BlogPostService.query();
//
//	$scope.deletePost = function (blogPost) {
//		blogPost.$remove(function () {
//			$scope.blogPosts = BlogPostService.query();
//		});
//	};
//}

function NewMeasurementController($scope,$routeParams, $location) {
    $scope.disable=true;
    $scope.newMeasurementEntry={}
    $scope.newMeasurementEntry.customer="Please enter a Batch Number";
    $scope.newMeasurementEntry.style="Please enter a Batch Number";

    $scope.checkBatchNo= function(){
    $scope.newMeasurementEntry.customer="Invalid Batch Number";
    $scope.newMeasurementEntry.style="Invalid Batch Number";

         if($scope.newMeasurementEntry.batchNo=="1234"){
             $scope.newMeasurementEntry.customer="YOUR CCutomer";
             $scope.newMeasurementEntry.style="Your style";
             $scope.batchNo=$scope.newMeasurementEntry.batchNo;
         }

    }
     switch ($routeParams.stage){
        case 'beforePresetting':
            $scope.stage=0;
            $scope.stageName='Before Presetting';
            break;
        case 'afterPresetting':
           $scope.stage=2;
           $scope.stageName='After Presetting';
           break;
        case 'afterDyeing':
           $scope.stage=3;
           $scope.stageName='After Dyeing';
           break;
        default:
           $location.path('/selectStage');

     }
	 $scope.sizes = [
        {id: 1, s: 'XS'},
        {id: 2, s: 'S'},
        {id: 3, s: 'M'},
        {id: 4, s: 'L'},
        {id: 5, s: 'XL'}
      ];

        $scope.save = function() {
		console.log("Saving : " + $scope.newMeasurementEntry)
	};
};


function AfterDyeingController($scope,$routeParams, $location, NewMeasurementService) {

	$scope.newMeasurementEntry = new NewMeasurementService();
	$scope.newMeasurementEntry = NewMeasurementService.get({id: $routeParams.id});

	 $scope.sizes = [
        {id: 1, s: 'XS'},
        {id: 2, s: 'S'},
        {id: 3, s: 'M'},
        {id: 4, s: 'L'},
        {id: 5, s: 'XL'}
      ];

	$scope.save = function() {
		$scope.newMeasurementEntry.$save(function() {
			$location.path('/');
		});
	};
};

function AfterPresettingController($scope,$routeParams, $location, NewMeasurementService) {

	$scope.newMeasurementEntry = new NewMeasurementService();
	$scope.newMeasurementEntry = NewMeasurementService.get({id: $routeParams.id});

	 $scope.sizes = [
        {id: 1, s: 'XS'},
        {id: 2, s: 'S'},
        {id: 3, s: 'M'},
        {id: 4, s: 'L'},
        {id: 5, s: 'XL'}
      ];

	$scope.save = function() {
		$scope.newMeasurementEntry.$save(function() {
			$location.path('/');
		});
	};
};

function BeforePresettingController($scope,$routeParams, $location
//, NewMeasurementService
) {

//	$scope.newMeasurementEntry = new NewMeasurementService();
//	$scope.newMeasurementEntry = NewMeasurementService.get({id: $routeParams.id});

	 $scope.sizes = [
        {id: 1, s: 'XS'},
        {id: 2, s: 'S'},
        {id: 3, s: 'M'},
        {id: 4, s: 'L'},
        {id: 5, s: 'XL'}
      ];

	$scope.save = function() {
		$scope.newMeasurementEntry.$save(function() {
			$location.path('/');
		});
	};
};


function ViewMeasurementsController($scope, NewMeasurementService) {

	$scope.newMeasurementEntries = NewMeasurementService.query();

	$scope.deleteEntry = function(newMeasurementEntry) {
		newMeasurementEntry.$remove(function() {
			$scope.newMeasurementEntries = NewMeasurementService.query();
		});
	};
};

//function EditController($scope, $routeParams, $location, BlogPostService) {
//
//	$scope.blogPost = BlogPostService.get({id: $routeParams.id});
//
//	$scope.save = function() {
//		$scope.blogPost.$save(function () {
//			$location.path('/');
//		});
//	};
//}

function FirstPageController($scope, $routeParams, $location) {
}


function SelectStageController($scope, $rootScope) {
}


function ViewBatchCtrl($scope,$routeParams, $location) {

params:{id:$routeParams.id}

};


function CreateController($scope, $location, BlogPostService) {

	$scope.blogPost = new BlogPostService();
	
	$scope.save = function() {
		$scope.blogPost.$save(function () {
			$location.path('/');
		});
	};
};


//function LoginController($scope, $rootScope, $location, $cookieStore, UserService) {
//
//	$scope.rememberMe = false;
//
//	$scope.login = function() {
//		UserService.authenticate($.param({username: $scope.username, password: $scope.password}), function(authenticationResult) {
//			var accessToken = authenticationResult.token;
//			$rootScope.accessToken = accessToken;
//			if ($scope.rememberMe) {
//				$cookieStore.put('accessToken', accessToken);
//			}
//			UserService.get(function(user) {
//				$rootScope.user = user;
//				$location.path("/");
//			});
//		});
//
//
//	};


function EditableTableCtrl($scope, $filter, $http, $q){
     $scope.entries = [
        {id: 1, gmt: 'Garment1',chestWidth: 0, hemWidth:0,cbLength:0,cfLength:0},
        {id: 2, gmt: 'Garment2'},
        {id: 3, gmt: 'Garment3'},
        {id: 4, gmt: 'Garment4'},
        {id: 5, gmt: 'Garment5'}
      ];

      $scope.statuses = [
        {value: 1, text: 'status1'},
        {value: 2, text: 'status2'},
        {value: 3, text: 'status3'},
        {value: 4, text: 'status4'}
      ];

  // save edits
      $scope.saveTable = function() {
        var results = [];
        console.log($scope.entries);

        };
}




function BatchListCtrl($scope){
     $scope.batches = [
        {id: 1, batchNo: '12341',styleName: 'ab111'},
        {id: 2, batchNo: '12342',styleName: 'ab112'},
        {id: 3, batchNo: '12343',styleName: 'ab113'},
        {id: 4, batchNo: '12344',styleName: 'ab112'},
        {id: 5, batchNo: '12345',styleName: 'ab112'}

      ];

      console.log($scope.batches);


      $scope.setSelected = function(index) {
          $scope.selected = $scope.ingredients[index];
          console.log($scope.selected);
      };
//      $scope.batches = BatchListService.query();

     $scope.setSelected = function() {
             $scope.selected = this.batch.id;
             console.log($scope.selected);
         };


   }

function AddSleeveCtrl($scope, $filter, $http, $q){
     $scope.entries = [
        {id: 1, gmt: 'Sleeve1'},
        {id: 2, gmt: 'Sleeve2'},
        {id: 3, gmt: 'Sleeve3'},
        {id: 4, gmt: 'Sleeve4'},
        {id: 5, gmt: 'Sleeve5'}
      ];

        // save edits
      $scope.saveTable = function() {
        var results = [];
        for (var i = $scope.entries.length; i--;) {
          var user = $scope.entries[i];
          // actually delete user
          if (entry.isDeleted) {
            $scope.entries.splice(i, 1);
          }
          // mark as not new
          if (entry.isNew) {
            entry.isNew = false;
          }

          // send on server
          results.push($http.post('/saveEntry', entry));
        }

        return $q.all(results);
      };
}


var services = angular.module('exampleApp.services', ['ngResource']);

//services.factory('UserService', function($resource) {
//
//	return $resource('rest/user/:action', {},
//			{
//				authenticate: {
//					method: 'POST',
//					params: {'action' : 'authenticate'},
//					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
//				}
//			}
//		);
//});

services.factory('BatchListService', function($resource) {

	return $resource('rest/new/:id', {id: '@id'});
});

services.factory('NewMeasurementService', function($resource) {

	return $resource('redsst/new', {id: '1'});
});
