
let abi = [
	{
		"constant": false,
		"inputs": [
			{
				"name": "dev",
				"type": "bytes32"
			}
		],
		"name": "remettre",
		"outputs": [
			{
				"name": "",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": false,
				"name": "hashDevoir",
				"type": "bytes32"
			},
			{
				"indexed": false,
				"name": "adr",
				"type": "address"
			}
		],
		"name": "RendreDevoir",
		"type": "event"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "destinataire",
				"type": "address"
			},
			{
				"name": "valeur",
				"type": "uint256"
			}
		],
		"name": "transfer",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "constructor"
	},
	{
		"constant": true,
		"inputs": [
			{
				"name": "",
				"type": "address"
			}
		],
		"name": "cred",
		"outputs": [
			{
				"name": "",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [
			{
				"name": "url",
				"type": "string"
			}
		],
		"name": "produireHash",
		"outputs": [
			{
				"name": "",
				"type": "bytes32"
			}
		],
		"payable": false,
		"stateMutability": "pure",
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
      console.log(dapp)
      contratCred();
    } catch(err) {
      // Gestion des erreurs
      console.error(err);
    }
   }

async function contratCred(){
    

    var contratCredibilite = new ethers.Contract("0x1a2bf6fca820a3c6767fc5ad4ea756adbe9aa4f1", abi, dapp.provider.getSigner() ); // init contrat

	contratCredibilite.on('RendreDevoir', (hashDevoir, adr) => { // Evénement à chaque ajout d'un devoir sur le contrat 0x1a2bf6fca820a3c6767fc5ad4ea756adbe9aa4f1
		document.getElementById("hashDevoir").innerHTML = "Le hash du devoir : " + hashDevoir ;
		document.getElementById("adr").innerHTML = "Adresse de celui qui l’a remis : " + adr ;
	});
}






