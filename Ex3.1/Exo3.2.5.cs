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
	uint internal dateLiquidation;
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
	function acheterTicketFestivale() public payable {
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

/**
 * @title Loterie
 * @dev Gestion de la loterie
 */
contract Loterie is CagnotteFestivale {

  /**
  * @dev Structure d'un ticket de loterie
  */
  struct TicketLoto{ 
    uint8 lotoNombre; // Nombre choisi 
    uint8 lotoJour; // Jour du tirage choisi 
    bool ticketGagant; // true si le ticket a le bon numéro
  }

  mapping (address => TicketLoto) loteries; 
  uint8 private nombreGagnant; // nombre gagnant du jour
  uint private dateTirage; // date du prochain tirage
  uint private gainLoto; // gain de la loterie
  uint8 private tirageDuJour; // Numéro du tirage du jour 

  /**
  * @dev constructeur du contrat
  */
  constructor() public {
    tirageDuJour = 0;
    gainLoto = 300 finney;
    dateTirage = SafeMath.add(now, 1 days);
  }

  /**
  * @dev Acheter un ticket de Loterie
  * @param jour nombre de jours avant le tirage
  * @param nombre choisi entre 1 et 255 compris 
  */
  function acheterTicketLoto(uint jour, uint nombre) public payable{
    require(festivaliers[msg.sender], "Vous devez faire parti des féstivaliers");
    require(jour > 0, "Le tirage d'aujourd'hui est déjà terminé");
    require(now + SafeMath.mul(jour, 1 days) < dateLiquidation, "Le festivale sera terminé avant le tirage");
    require(loteries[msg.sender].lotoJour <= tirageDuJour, "Vous avez déjà un ticket en cours");
    require(msg.value >= 100 finney, "Ticket loto à 100 finney");
    require(nombre <= 255 && nombre > 0, "Le nombre doit être entre 1 et 255 compris");
    loteries[msg.sender] = TicketLoto({ lotoNombre : uint8(nombre), lotoJour : uint8(SafeMath.add(tirageDuJour, jour)), ticketGagant : false });
  }

  /**
  * @dev Tirage loto du jour 
  */
  function lotoTirageDuJour() public {
    require(dateTirage <= now, "Le tirage d'aujourd'hui est déjà effectué");
    dateTirage += 1 days; // prochain tirage dans un jour
    tirageDuJour++; // nombre de tirage +1
    nombreGagnant = uint8(blockhash(block.number-1));// hash du dernier block sur 8 bits
  }

  /**
  * @dev les gagnants peuvent retirer leur gain
  */
  function retirerGain() public {
    require(loteries[msg.sender].lotoJour == tirageDuJour, "Le tirage du jour ne concerne pas votre ticket");
    require(loteries[msg.sender].lotoNombre == nombreGagnant, "Vous n'avez pas le bon numéro");
    require(!loteries[msg.sender].ticketGagant, "Vous avez déjà récupéré votre prix");
    loteries[msg.sender].ticketGagant = true; 
    msg.sender.transfer(gainLoto); // transfer du gain 
  }
}

/**
 * @title SafeMath
 * @dev Unsigned math operations with safety checks that revert on error
 */
library SafeMath {
    /**
     * @dev Multiplies two unsigned integers, reverts on overflow.
     */
    function mul(uint256 a, uint256 b) internal pure returns (uint256) {
        // Gas optimization: this is cheaper than requiring 'a' not being zero, but the
        // benefit is lost if 'b' is also tested.
        // See: https://github.com/OpenZeppelin/openzeppelin-solidity/pull/522
        if (a == 0) {
            return 0;
        }

        uint256 c = a * b;
        require(c / a == b);

        return c;
    }

    /**
     * @dev Integer division of two unsigned integers truncating the quotient, reverts on division by zero.
     */
    function div(uint256 a, uint256 b) internal pure returns (uint256) {
        // Solidity only automatically asserts when dividing by 0
        require(b > 0);
        uint256 c = a / b;
        // assert(a == b * c + a % b); // There is no case in which this doesn't hold

        return c;
    }

    /**
     * @dev Subtracts two unsigned integers, reverts on overflow (i.e. if subtrahend is greater than minuend).
     */
    function sub(uint256 a, uint256 b) internal pure returns (uint256) {
        require(b <= a);
        uint256 c = a - b;

        return c;
    }

    /**
     * @dev Adds two unsigned integers, reverts on overflow.
     */
    function add(uint256 a, uint256 b) internal pure returns (uint256) {
        uint256 c = a + b;
        require(c >= a);

        return c;
    }

    /**
     * @dev Divides two unsigned integers and returns the remainder (unsigned integer modulo),
     * reverts when dividing by zero.
     */
    function mod(uint256 a, uint256 b) internal pure returns (uint256) {
        require(b != 0);
        return a % b;
    }
}
