
let abi = [
	{
		"constant": false,
		"inputs": [
			{
				"name": "s",
				"type": "string"
			}
		],
		"name": "ajouterCarte",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [],
		"name": "nbrCartes",
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
				"name": "ind",
				"type": "uint256"
			}
		],
		"name": "recuperer",
		"outputs": [
			{
				"name": "",
				"type": "string"
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
			document.getElementById("corps").style.display="block";
			document.getElementById("coMetaMask").style.display="none";
            
    } catch(err) {
      // Gestion des erreurs
      console.error(err);
    }
   }

async function ajoutImgIPFS() {

    const ipfs = window.IpfsHttpClient('localhost', '5001')

	let image = document.getElementById('addImg').files[0]

	const reader = new FileReader();

	reader.readAsArrayBuffer(image);

	reader.onloadend = function() {

		ipfs.add(new ipfs.types.Buffer.from(reader.result), (err, files) => {

			if (err) return console.error(err)
			console.log(files[0].hash)
			ajoutHachImg(files[0].hash)
		})
	}
}

async function ajoutHachImg(imgHash){

    var contratCollection = new ethers.Contract("0x0a4bb3fd6afd193d40d0a126bb9e99ebe92d46b8", abi, dapp.provider.getSigner());

	await contratCollection.ajouterCarte(imgHash);
}

async function recupHachImg(indexHash) {

	var contratCollection = new ethers.Contract("0x0a4bb3fd6afd193d40d0a126bb9e99ebe92d46b8", abi,  dapp.provider);	
	var hash = await contratCollection.recuperer(indexHash);

	const ipfs = window.IpfsHttpClient('localhost', '5001')

    ipfs.cat(hash, (err, data) => {
		if (err) return console.error(err)
				
		var img = new Image();
		img.src = "data:image/jpeg;base64, " + data.toString("base64");
		img.height = 200;
		img.width = 200;
		var element = document.getElementById("diximage");
		element.appendChild(img)

    })

}

async function affDixImg() {

	var contratCollection = new ethers.Contract("0x0a4bb3fd6afd193d40d0a126bb9e99ebe92d46b8", abi,  dapp.provider);
	
	var nbrImage = await contratCollection.nbrCartes();

	var list = document.getElementById("diximage");

		while (list.hasChildNodes()) {
			list.removeChild(list.firstChild);
		}

	for (let i = 0 ; i < nbrImage && i < 10; i++){
		await recupHachImg(i)
	}

}






