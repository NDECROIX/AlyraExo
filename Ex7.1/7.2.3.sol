pragma solidity ^0.5.1;

/**
* @title ERC721Simple
*/
contract ERC721Simple {
    
    event Transfer(address indexed _from, address indexed _to, uint256 _tokenId);
    event Approval(address indexed _owner, address indexed _approved, uint256 _tokenId);
    
    function balanceOf(address _owner) public view returns (uint256 _balance);
    function ownerOf(uint256 _tokenId) public view returns (address _owner);
    function transferFrom(address _to, uint256 _tokenId) public;
    function approve(address _to, uint256 _tokenId) public;
    function takeOwnership(uint256 _tokenId) public;
 
}
 
/**
* @title ObjetsMagiques
*/   
contract ObjetsMagiques {
    
    uint[] objetMagique;
    
    mapping (address => uint) ownerObjetMagiqueCount;
    
    mapping (uint => address) objetMagiqueOwner;
    
    address payable tresorier;
    
    constructor () public {
        tresorier = msg.sender;
    }
    
    /**
	* @dev Créée un objet magique pour 0.1 ether
	* @return uint de l'objet magique
	*/
    function creuser() public payable returns(uint){
        require(msg.value >= 0.1 ether, "Prix de fabrication 0.1 ether");
        
        uint objetCreee = ((uint) (blockhash(block.number-1))) % 3000;
        
        require(!objetMagiqueExist(objetCreee));
        
        tresorier.transfer(msg.value);
        
        ownerObjetMagiqueCount[msg.sender]++;
        objetMagiqueOwner[objetCreee] = msg.sender;
        objetMagique.push(objetCreee);
        
		return objetCreee;
    }
    
    /**
	* @dev Utilise l'objet magique
	* @param _objetMagique num de l'objet magique
	*/
    function utiliser(uint _objetMagique) public returns(uint){
        require(objetMagiqueOwner[_objetMagique] == msg.sender);
        uint chance = ((uint) (blockhash(block.number-1))) % 10;
        if  (chance == 0 ){
            ownerObjetMagiqueCount[msg.sender]--;
            delete objetMagiqueOwner[_objetMagique];
            objetMagiqueDetruire(_objetMagique);
        }
        return chance;
    }
    
    /**
	* @dev Vérifie si l'objet magique existe
	* @param _objetMagique num de l'objet magique
	*/
    function objetMagiqueExist(uint _objetMagique) internal view returns(bool){
        for (uint8 i = 0; i < objetMagique.length; i++){
            if(objetMagique[i] == _objetMagique) return true;
        }
    }
    
    /**
	* @dev Détruit un objet magique
	* @param _objetMagique num de l'objet magique
	*/
    function objetMagiqueDetruire(uint _objetMagique) private {
        for (uint8 i = 0; i < objetMagique.length; i++){
            if(objetMagique[i] == _objetMagique){
                objetMagique[i] = objetMagique[objetMagique.length-1];
                delete objetMagique[objetMagique.length-1];
            } 
        }
    }
}

/**
* @title ObjetsMagiquesOwnership
*/
contract ObjetsMagiquesOwnership is ObjetsMagiques, ERC721Simple {
    
    mapping(uint256 => address) tokenApprovals;
    
    function verifApprouve(uint _tokenId) public view returns(bool){
        if (tokenApprovals[_tokenId] == msg.sender) return true;
    }
    
    function balanceOf(address _owner) public view returns (uint256 _balance){
        return ownerObjetMagiqueCount[_owner];
    }
     
    function ownerOf(uint256 _tokenId) public view returns (address _owner){
        require(objetMagiqueExist(_tokenId));
        return objetMagiqueOwner[_tokenId];
    }
     
    function exists(uint256 _tokenId) public view returns (bool exist){
        return objetMagiqueExist(_tokenId);
    }
    
    /**
	* @dev transférer un ojet magique
	* @param _from  adresse du propriétaire
	* @param _to  adresse de destination
	* @param _tokenId num de l'objet magique
	*/
    function _transfer(address _from, address _to, uint256 _tokenId) private {
        objetMagiqueOwner[_tokenId] = _to;
        ownerObjetMagiqueCount[_from]--;
        ownerObjetMagiqueCount[_to]++;
        emit Transfer( _from, _to, _tokenId);
    }
    
    /**
	* @dev transférer un ojet magique
	* @param _to  adresse de destination
	* @param _tokenId num de l'objet magique
	*/
    function transferFrom( address _to, uint256 _tokenId) public {
        require( _tokenId < 2000 );
        require(objetMagiqueOwner[_tokenId] == msg.sender);
        _transfer(msg.sender, _to, _tokenId);

    }
    
    /**
	* @dev Approuver le changement de possession d'un ojet magique
	* @param _to  adresse du prochain propriétaire
	* @param _tokenId num de l'objet magique
	*/
    function approve(address _to, uint256 _tokenId) public  {
        require( _tokenId < 2000 );
        require(ownerOf(_tokenId) == msg.sender);
        tokenApprovals[_tokenId] = _to;
        emit Approval(msg.sender, _to, _tokenId);
    }
    
    /**
	* @dev Prendre possession d'un objet magique approuvé
	* @param _tokenId num de l'objet magique
	*/
    function takeOwnership(uint256 _tokenId) public {
        require( _tokenId < 2000 );
        require(tokenApprovals[_tokenId] == msg.sender);
        address owner = ownerOf(_tokenId);
        _transfer(owner, msg.sender, _tokenId);
    }
    
    
    
}

contract Bazar {
    
    struct Enchere {
    
        address meilleurAcheteur;
        uint256 meilleureOffre;
        uint256 finEnchere;
        uint256 objet;
        uint mecanisme;
        uint reduction;
        uint seuille;
        address payable vendeur;
        bool cloturer;
        
    }
    
    Enchere[] encheres;
    
    ObjetsMagiquesOwnership objetsMagiques;
    uint public dureeEnchere;

    // Remboursements d'enchères précédentes
    mapping(address => uint) echecEnchere;
    
    // paiement d'enchères précédentes
    mapping(address => uint) enchereFini;
    
    constructor(address _objetMagique) public {
        objetsMagiques = ObjetsMagiquesOwnership(_objetMagique);
        dureeEnchere = 1000;
    } 
    
    /**
	* @dev Proposer un objet magique à la vente
	* @param _objet num de l'objet magique
	*/
    function proposerALaVente(uint _objet, uint _mecanisme, uint _offre, uint _reduction, uint _seuille) public {
        require(objetsMagiques.ownerOf(_objet) == msg.sender, "Vous n'êtes pas propriétaire de cette objet ");
        require(objetsMagiques.verifApprouve(_objet), "Vous devez approuver le changement de propriétaire");
        require(_mecanisme <= 1, "Mecanisme inexistant");
        
        objetsMagiques.takeOwnership(_objet); // on donne la propriété au contrat
        
        if ( _mecanisme == 0 ){
            encheres.push(Enchere({ meilleurAcheteur : msg.sender,
                                meilleureOffre : 0, 
                                finEnchere : block.number + dureeEnchere,
                                objet : _objet,
                                mecanisme : 0,
                                reduction : 0,
                                seuille : 0,
                                vendeur : msg.sender, 
                                cloturer : false }));
        }
        else
        {
            encheres.push(Enchere({ meilleurAcheteur : msg.sender,
                                meilleureOffre : _offre, 
                                finEnchere : block.number + dureeEnchere,
                                objet : _objet,
                                mecanisme : 1,
                                reduction : _reduction,
                                seuille : _seuille,
                                vendeur : msg.sender, 
                                cloturer : false }));
        }
        
    }
    
    /**
	* @dev Proposer une offre
	* @param indice de l'enchère
	*/
    function offreClassique(uint indice) public payable {
        require(encheres[indice].mecanisme == 0, "Ceci est une enchère holandaise");
        require(block.number < encheres[indice].finEnchere, "Enchère terminée" );
        require(msg.value > encheres[indice].meilleureOffre, "offre trop faible");
        require(msg.sender != encheres[indice].vendeur, "Vous ne pouvez pas enchérir sur votre objet");
        
        if(encheres[indice].meilleureOffre != 0){
            echecEnchere[encheres[indice].meilleurAcheteur] += encheres[indice].meilleureOffre;
        }
        
        encheres[indice].meilleurAcheteur = msg.sender;
        encheres[indice].meilleureOffre = msg.value;
        
    }
    
    /**
	* @dev Proposer une offre
	* @param indice de l'enchère
	*/
    function offreHolandaise(uint indice) public payable {
        require(encheres[indice].mecanisme == 1, "Ceci est une enchère classique");
        require(!encheres[indice].cloturer, "Enchère cloturée");
        require(block.number < encheres[indice].finEnchere, "Enchère terminée" );
        require(msg.value > calculOffreH(indice), "offre trop faible");
        require(msg.sender != encheres[indice].vendeur, "Vous ne pouvez pas enchérir sur votre objet");
        
        encheres[indice].cloturer = true;
        
        enchereFini[encheres[indice].vendeur] += msg.value;
        
    }
    
    /**
	* @dev Calcul de l'offre actuel d'une enchère holandaise
	* @param indice de l'enchère
	*/
    function calculOffreH(uint indice) private view returns (uint){
        uint blockDif = block.number + 1000 - encheres[indice].finEnchere;
        uint reduction = blockDif * encheres[indice].reduction;
        
        if ((reduction + encheres[indice].seuille) < encheres[indice].meilleureOffre ){
            return encheres[indice].meilleureOffre - reduction;
        }
        else {
            return encheres[indice].seuille;
        }
    }
    
    /// Retirer l'argent d'une enchère qui a échoué
    function remboursement() public {
        
        uint montant = echecEnchere[msg.sender];
        
        if (montant > 0) {
            echecEnchere[msg.sender] = 0;
            msg.sender.transfer(montant);
        }
    }
    
    /**
	* @dev forcer une enchére si l'acquéreur
	* @param indice de l'enchère
	*/
    function cloturerEnchere(uint indice) public {
        require(block.number >= encheres[indice].finEnchere, "Enchère en cours");
        require(!encheres[indice].cloturer, "Enchère cloturée");
        
        encheres[indice].cloturer = true;
        
        if (encheres[indice].mecanisme == 0){
            
            if(encheres[indice].meilleureOffre != 0){
                enchereFini[encheres[indice].vendeur] += encheres[indice].meilleureOffre;
            }
        }
        else{
            encheres[indice].meilleureOffre = 0;
        }
         
    }
    
    /**
	* @dev Recuperer un Objet 
	* @param indice de l'enchère
	*/
    function recupererObjet(uint indice) public {
        require(block.number >= encheres[indice].finEnchere, "Enchère en cours");
        require(encheres[indice].meilleurAcheteur == msg.sender, "Vous n'êtes pas l'acquéreur de cette enchère");
        require(encheres[indice].cloturer, "Enchère en cours");
        require(objetsMagiques.ownerOf(indice) == address(this), "Enchère déjà récupérée ");
        
        objetsMagiques.transferFrom(encheres[indice].meilleurAcheteur, encheres[indice].objet); 
        
    }
    
    /**
	* @dev Recuperer des gains 
	*/
    function recupererGain() public {
        require(enchereFini[msg.sender] != 0);
        uint montant = enchereFini[msg.sender];
        enchereFini[msg.sender] = 0;
        msg.sender.transfer(montant);
    }
}










