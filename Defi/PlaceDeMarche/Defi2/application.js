
let abi = [ 
	{
		"constant": true,
		"inputs": [
			{
				"name": "",
				"type": "uint256"
			}
		],
		"name": "demandes",
		"outputs": [
			{
				"name": "statut",
				"type": "uint8"
			},
			{
				"name": "adresseDemandeur",
				"type": "address"
			},
			{
				"name": "delai",
				"type": "uint256"
			},
			{
				"name": "projet",
				"type": "string"
			},
			{
				"name": "reputation",
				"type": "uint256"
			},
			{
				"name": "remuneration",
				"type": "uint256"
			},
			{
				"name": "urlDepot",
				"type": "bytes32"
			},
			{
				"name": "candidatRetenu",
				"type": "address"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [],
		"name": "nombreDemandes",
		"outputs": [
			{
				"name": "",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	}
];


async function createMetaMaskDapp() {
    try {
      // Demande à MetaMask l'autorisation de se connecter
      const addresses = await ethereum.enable();
      const address = addresses[0]
      // Connection au noeud fourni par l'objet web3
      const provider = new ethers.providers.Web3Provider(ethereum);
      dapp = { address, provider };
			console.log(dapp);
			document.getElementById("offres").style.display="block";
    } catch(err) {
      // Gestion des erreurs
      console.error(err);
    }
   }

async function contratPDM(){

    var contratPlaceDeMarche = new ethers.Contract("0x6ff3ac33ebe1753b150705426a18768d58a0bdd7", abi, dapp.provider ); 
		
		var list = document.getElementById("ListeDemandes");

		while (list.hasChildNodes()) {
			list.removeChild(list.firstChild);
		}

		var numberOfDemande = await contratPlaceDeMarche.nombreDemandes();

		for (var i = 0; i < numberOfDemande; ++i) {

			var demande = await contratPlaceDeMarche.demandes(i);

			if(demande.statut == 0 ){

					var listItem = document.createElement('li');

					listItem.innerHTML = '<p>Demande '+ i + ' : ' + demande.projet + '<br>' +
																		'Delai : ' + demande.delai + ' jours <br>' +
																		'Rémunération : ' + demande.remuneration + ' wei <br>' +
																		'Réputaion : ' + demande.reputation + ' minimum </p><br>';

					document.getElementById("ListeDemandes").appendChild(listItem);
			}			
		}
}






