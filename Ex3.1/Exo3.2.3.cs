pragma solidity ^0.4.25;
/**
 * @title SceneOuverte
 */
contract SceneOuverte {

	string[12] passageArtistes;
	uint creneauxLibres = 12;
	uint tour;

	/**
	* @dev Ajoute un artiste aux creneauxLibres
	* @param nomDArtiste de l'artiste
	*/
	function sInscrire(string nomDArtiste) public {
		if  (creneauxLibres > 0){
			passageArtistes[12-creneauxLibres] = nomDArtiste;
			creneauxLibres--;
		}
	}

	/**
	* @dev Passe à l'artiste suivant 
	*/
	function passerArtisteSuivant() public {
		if ( SafeMath.add(tour, creneauxLibres) < 12 ){
		tour++;
	}

	}

	/**
	* @dev return l'artiste en cours 
	* @return le nom de l'artiste en cours ou FIN
	*/
	function artisteEnCours() public view returns (string) {
		if  (SafeMath.add(tour, creneauxLibres) < 12 ){
			return passageArtistes[tour];
		}
		else {
			return "FIN";
		}

	}

}
/**
* @title Assemblee
*/
contract Assemblee {

	struct Decision {
		string descriptionDecision;
		uint votesPour;
		uint votesContre;
		mapping (address => bool) aVote;
	}

	address[] administrateurs;
	string nomAssemble;
	address[] membres;

	/**
	* @dev Constructeur qui init un admin est un nom d'assemblée
	* @param nom de l'assemblée
	*/
	constructor(string nom) public{
		administrateurs.push(msg.sender);
		nomAssemble = nom;
	}

	/**
	* @dev Permet de rejoindre l'assemblée
	*/
	function rejoindre() public {
		membres.push(msg.sender); 
	}

	/**
	* @dev return true si l'adresse de l'utilisateur en paramètre est un membre
	* @param utilisateur à vérifier
	* @return vrais si membre de l'assemeblée
	*/
	function estMembre(address utilisateur) public view returns (bool){
		for(uint i = 0; i < membres.length; i++){
			if (membres[i] == utilisateur) return true;
		}
	}

	/**
	* @dev return true si l'adresse en paramètre est un administrateur
	* @param administrateur à vérifier
	* @return vrais si l'adresse appartient bien à un admin
	*/
	function estAdministrateur(address administrateur) public view returns (bool){
		for(uint i = 0; i < administrateurs.length; i++){
			if (administrateurs[i] == administrateur) return true;
		}
	}

	/**
	* @dev Un admin peut nommer un admin
	* @param administrateur adresse à nommer administrateur
	*/
	function nommerAdministrateur(address administrateur) public {
		require(estAdministrateur(msg.sender));
		administrateurs.push(administrateur);
	}

	/**
	* @dev Un Admin peut démissionner 
	*/
	function demissionAdministrateur() public {
		require(estAdministrateur(msg.sender));
		for (uint i = 0; i < administrateurs.length; i++){
			if  (administrateurs[i] == msg.sender){
				delete administrateurs[i];
				break;
			} 
		}
	}

	//Décisions collectives

	Decision[] decisions;
	address[] participants;

	/**
	* @dev Participé aux décisions collective
	*/
	function participer() public {
		require(estMembre(msg.sender), "Il faut être membre de l'assemblée!");
		participants.push(msg.sender); 
	}

	/**
	* @dev Retourn vrais si l'adresse en paramètre participe aux décisions 
	* @param utilisateur à vérifier
	* @return vrais si il est participant
	*/
	function estParticipant(address utilisateur) public view returns (bool){
		for(uint i = 0; i < participants.length; i++){
			if (participants[i] == utilisateur) return true;
		}
	}

	/**
	* @dev Les participants peuvent proposer des décisions
	* @param description de la proposition à soumettre
	*/
	function proposerDecision(string description) public {
		require(estParticipant(msg.sender), "Il faut être participant!");
		decisions.push(Decision({descriptionDecision : description, votesPour : 0, votesContre : 0}));
	}

	/**
	* @dev permet de voter une décisions
	* @param proposition : proposition pour laquelle l'on veut soumettre son vote
	* @param pourContre : 1 être pour la propostion et 0 être contre la proposition
	*/
	function voter(uint proposition, uint pourContre) public {
		require(decisions[proposition].aVote[msg.sender] == false);
		if (pourContre == 1) decisions[proposition].votesPour++;
		else decisions[proposition].votesContre++;
		decisions[proposition].aVote[msg.sender] = true;

	}

	/**
	* @dev Comptabilise les votes
	* @param indice de la proposition
	* @return la différence entre les votes
	*/
	function comptabiliser(uint indice) public view returns (int){
		return int(decisions[indice].votesPour - decisions[indice].votesContre);
	} 

	/**
	* @dev Un Admin peut fermer une proposition de décision
	* @param indice de la proposition à fermer
	*/
	function fermerPropositionDecision(uint indice) public {
		require(estAdministrateur(msg.sender), "Il faut être administrateur!");
		delete decisions[indice];
	}
}

/**
 * @title Cogere
 */
contract Cogere {

	mapping (address => uint) organisateurs;

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

	/**
	* @dev Init le nombre de places à 500
	*/
	constructor() public {
		nombrePlaces = 500;
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
		require(estOrga(msg.sender));
		require(destinataire != address(0));
		require(montant>0);
		destinataire.transfer(montant);// l'absence de payable n'empêche pas l'utilisation de la fonction transfer()
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
