
async function createMetaMaskDapp() {
    try {
      // Demande à MetaMask l'autorisation de se connecter
      const addresses = await ethereum.enable();
      const address = addresses[0]
      // Connection au noeud fourni par l'objet web3
      const provider = new ethers.providers.Web3Provider(ethereum);
      dapp = { address, provider };
      console.log(dapp)
	  callData();
    } catch(err) {
      // Gestion des erreurs
      console.error(err);
    }
    
   }

async function balance(){
    dapp.provider.getBalance(dapp.address).then((balance) => {
        let etherString = ethers.utils.formatEther(balance);
        console.log("Balance: " + etherString);
    });
}

async function lastBlockNumber(){
    dapp.provider.getBlockNumber(dapp.address).then((blockNumber) => {
        console.log("Current block number: " + blockNumber);
        document.getElementById("lastBlockNumber").innerHTML = "Numéro du dernier bloc : " +  blockNumber;
    });
}

async function gasPrice(){
    dapp.provider.getGasPrice(dapp.address).then((gasPrice) => {
        // gasPrice is a BigNumber; convert it to a decimal string
        let gasPriceString = gasPrice.toString();
        let gasEtherString = ethers.utils.formatEther(gasPrice);
        console.log("Current gas price: " + gasPriceString);
        document.getElementById("gasPrice").innerHTML =  "Prix du gaz : " + gasEtherString + " Ether";
    });
}

function callData() {
  balance();
  lastBlockNumber();
  gasPrice();
}

