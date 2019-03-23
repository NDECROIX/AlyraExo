pragma solidity ^0.5.1;

/**
* @title ERC721Simple
*/
contract ERC721Simple {
    
    event Transfer(address indexed _from, address indexed _to, uint256 _tokenId);
    
    function balanceOf(address _owner) public view returns (uint256 balance);
     
    function ownerOf(uint256 _tokenId) public view returns (address _owner);
     
    function exists(uint256 _tokenId) public view returns (bool exist);
    
    function transferFrom(address _from, address _to, uint256 _tokenId) public;
 
}
 
/**
* @title ObjetsMagiques
*/   
contract ObjetsMagiques {
    
    uint[] public objetMagique;
    
    mapping (address => uint) ownerObjetMagiqueCount;
    
    mapping (uint => address) objetMagiqueOwner;
    
    address payable tresorier;
	
	constructor () public {
        tresorier = msg.sender;
    }
    
    /**
	* @dev Créée un objet magique pour 0.1 ether
	*/
    function creuser() public payable {
        require(msg.value >= 0.1 ether, "Prix de fabrication 0.1 ether");
        
        uint objetCreee = ((uint) (blockhash(block.number-1))) % 2999;
        
        require(!objetMagiqueExist(objetCreee));
        
        tresorier.transfer(msg.value);
        
        ownerObjetMagiqueCount[msg.sender]++;
        objetMagiqueOwner[objetCreee] = msg.sender;
        objetMagique.push(objetCreee);
        
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
    
    function balanceOf(address _owner) public view returns (uint256 balance){
        return ownerObjetMagiqueCount[_owner];
    }
     
    function ownerOf(uint256 _tokenId) public view returns (address _owner){
        require(objetMagiqueExist(_tokenId));
        return objetMagiqueOwner[_tokenId];
    }
     
    function exists(uint256 _tokenId) public view returns (bool exist){
        return objetMagiqueExist(_tokenId);
    }
    
    function transferFrom(address _from, address _to, uint256 _tokenId) public {
        require( _tokenId < 2000 );
        require(objetMagiqueOwner[_tokenId] == _from);
        require(objetMagiqueOwner[_tokenId] == msg.sender);
        objetMagiqueOwner[_tokenId] = _to;
        ownerObjetMagiqueCount[_from]--;
        ownerObjetMagiqueCount[_to]++;
        
        emit Transfer( _from, _to, _tokenId);
    }
    
}










