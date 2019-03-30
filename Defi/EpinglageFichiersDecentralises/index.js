
let abi = [
	{
		"constant": false,
		"inputs": [
			{
				"name": "_tresorier",
				"type": "address"
			}
		],
		"name": "changeTresorier",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": false,
				"name": "hash",
				"type": "string"
			}
		],
		"name": "Epingle",
		"type": "event"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "hash",
				"type": "string"
			}
		],
		"name": "payerStockage",
		"outputs": [],
		"payable": true,
		"stateMutability": "payable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"name": "_tresorier",
				"type": "address"
			}
		],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "constructor"
	}
];

async function connectMetaMask() {
    try {
      // Demande à MetaMask l'autorisation de se connecter
      const addresses = await ethereum.enable();
      const address = addresses[0]
      // Connection au noeud fourni par l'objet web3
      const provider = new ethers.providers.Web3Provider(ethereum);
      dapp = { address, provider };

			console.log(dapp);
			document.getElementById("corps").style.display="block";
			document.getElementById("coMetaMask").style.display="none";
            
    } catch(err) {
      // Gestion des erreurs
      console.error(err);
    }
   }

async function pinFile(hashIPFS) {
	
	const ipfs = new Ipfs({ repo: 'ipfs-' + Math.random() }) //window.IpfsHttpClient('localhost', '5001')
	
	ipfs.once('ready', () => {
      console.log('Online status: ', ipfs.isOnline() ? 'online' : 'offline')
      document.getElementById("status").innerHTML= 'Noeud status: ' + (ipfs.isOnline() ? 'online' : 'offline')

		ipfs.pin.add(hashIPFS, (err, files) => {
            
			if (err) return console.error(err)
			console.log('Epingler : ' + files[0].hash)
			document.getElementById("epingler").innerHTML='Fichier épinglé : ' + files[0].hash
			
		})
	  
    })
	
	
	document.getElementById("Loader").style.display="none";
	
}

async function ajoutHashFile(fileHash){

	document.getElementById("Loader").style.display="block";
	
	var contratEpinglage = new ethers.Contract("0x803cdf82c52f48ae47859fbbdb85b528f00d2681", abi, dapp.provider.getSigner());
	
	var overrideOptions = {
		value: ethers.utils.parseEther('0.1')
	};

	let tx = await contratEpinglage.payerStockage(fileHash, overrideOptions);

	contratEpinglage.on('Epingle', (hash) => { // Evénement à chaque ajout d'un hash sur le contrat Epinglage
	pinFile(fileHash)
	})
    
}

async function ajoutFileIPFS() {

	const ipfs = new Ipfs({ repo: 'ipfs-' + Math.random() }) //window.IpfsHttpClient('localhost', '5001')
	
	ipfs.once('ready', () => {
      console.log('Online status: ', ipfs.isOnline() ? 'online' : 'offline')
      document.getElementById("status").innerHTML= 'Node status: ' + (ipfs.isOnline() ? 'online' : 'offline')
	  
	  let file = document.getElementById('addFile').files[0]

		const reader = new FileReader();

		reader.readAsArrayBuffer(file);

		reader.onloadend = function() {

			ipfs.add(new ipfs.types.Buffer.from(reader.result), {pin : true}, (err, files) => {    
				if (err) return console.error(err)
				console.log('Hash IPFS : ' + files[0].hash)
				document.getElementById("hashReturn").innerHTML = files[0].hash
			})
		}
	  
	  
    })

	

}







