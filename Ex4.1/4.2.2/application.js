
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
      contratCredibilite();
    } catch(err) {
      // Gestion des erreurs
      console.error(err);
    }
   }

async function contratCredibilite(){
    var privateKey = ''; // Clé privée pour signer
    var wallet = new ethers.Wallet(privateKey, dapp.provider);
    wallet.defaultGasLimit = 3000000; // on limite le gaz 

    var contratCredibilite = new ethers.Contract("0xedf791E6dD181b7F9eF1476c42829613C40FB11D", abi, wallet ); // init du contrat

    var maCredibilite = await contratCredibilite.cred(dapp.address); 
    var monDevoirHash = await contratCredibilite.produireHash("https://github.com/NDECROIX/AlyraExo/tree/master/Ex4.1"); // on hash l'url d'un devoir

    var ordreArriver = await contratCredibilite.remettre(monDevoirHash); // on remet le devoir
    await ordreArriver.wait(); // on attend que la fonction se termine 

    var gainCredibilite = await contratCredibilite.cred(dapp.address) - maCredibilite; // on calcul le gain de crédibilité 
    console.log(parseInt(gainCredibilite));
}




