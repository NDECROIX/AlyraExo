pragma solidity ^0.4.25;
/**
 * @title Cogere
 */
contract Cogere {

	mapping (address => uint) public organisateurs;
  uint internal nombreParts = 100;

	/**
	*  @dev Init le nombre de parts à 100
	*/
	constructor() public {
		organisateurs[msg.sender] = 100;
	}

	/**
	* @dev Ajoute un organisateur en lui transmettant des parts
	* @param orga : adresse de l'organisateur à ajouter
	* @param parts : nombre de parts en pourcentage à transmettre
	*/
	function transferOrga(address orga, uint parts) public {
		uint transfertParts = SafeMath.div(SafeMath.mul(parts, organisateurs[msg.sender]), 100);
		organisateurs[msg.sender] -= transfertParts;
		organisateurs[orga] = transfertParts; 
	}

	/**
	* @dev Retourn vrais si l'adresse en paramètre est un organisateur
	* @param orga à vérifier
	* @return Vrais si il est organisateur
	*/
	function estOrga(address orga) public view returns (bool) {
		if  (organisateurs[orga] != 0) return true;
	}

}

/**
 * @title CagnotteFestivale
 */
contract CagnotteFestivale is Cogere {  

	mapping (address => bool) festivaliers;
	uint private depensesTotales;
	string[] private sponsors;
	uint private nombrePlaces;

	uint private dateFestival;
	uint private dateLiquidation;
	uint private montantGain;

	uint private dateSeuil;
	uint private seuilDepense;

	/**
	* @dev Init le nombre de places à 500
	*/
	constructor() public {
		nombrePlaces = 500;
    dateFestival = now;
    dateLiquidation = SafeMath.add(dateFestival, 2 weeks);
    dateSeuil = SafeMath.add(now, 1 days);
    seuilDepense = 10 ether;
	}
	
	/**
	* @dev Fonction qui permet d’accepter les paiements et dons anonymes
	*/
	function () external payable{}

	/**
	* @dev Acheter un ticket à 500 finney
	*/
	function acheterTicket() public payable {
		require(nombrePlaces > 0);
		require(msg.value >= 500 finney, "Place à 0,5 Ethers");
		festivaliers[msg.sender] = true;
		nombrePlaces--;
	}

	/**
	* @dev permet aux organisteurs de payer
	* @param destinataire du paiment
	* @param montant du paiment
	*/
	function payer(address destinataire, uint montant) public { //payable ne fonctionne pas
    require(controlDepense(montant));
		require(estOrga(msg.sender));
		require(destinataire != address(0));
		require(montant>0);
		destinataire.transfer(montant);// l'absence de payable n'empêche pas l'utilisation de la fonction transfer()
	}

	/**
	* @dev control si le montant ne dépasse pas le seuil actuel
	* @param montant du paiment à controler
	*/
	function controlDepense(uint montant) internal returns (bool) {

		while (dateSeuil < now){
			dateSeuil = SafeMath.add(dateSeuil, 1 days);
			seuilDepense = 10 ether;
		}
		if  (montant <= seuilDepense){
			seuilDepense = SafeMath.sub(seuilDepense, montant);
			return true;
		}

	}

	/**
	* @dev Ajoute une dépense aux dépenses totales 
	* @param montant à ajouter aux dépenses
	*/
	function comptabiliserDepense(uint montant) private {
		depensesTotales = SafeMath.add(depensesTotales, montant);
	}

	/**
	* @dev Ajoute un sponsor à la liste des sponsors pour 30 ether
	* @param nom du sponsor
	*/
	function sponsoriser(string memory nom) public payable{
		require(msg.value >= 30 ether);
		sponsors.push(nom);
	}
	
	/**
	* @dev retrait des organisateurs avec leurs parts;
	*/
	function retraitOrga() internal {
		require(estOrga(msg.sender));
		require(block.timestamp >= dateLiquidation);
		
		if(montantGain == 0) montantGain = SafeMath.div(address(this).balance, 100);
		if(SafeMath.sub(nombreParts, organisateurs[msg.sender]) > 0){
			msg.sender.transfer(SafeMath.mul(organisateurs[msg.sender], montantGain));
			nombreParts = SafeMath.sub(nombreParts, organisateurs[msg.sender]);
		}
		else{
			selfdestruct(msg.sender);
		}
	}
}