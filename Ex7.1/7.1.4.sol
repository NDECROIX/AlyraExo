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
	* @dev Vérifie si l'objet magique existe
	* @param _objetMagique num de l'objet magique
	*/
    function objetMagiqueExist(uint _objetMagique) internal view returns(bool){
        for (uint8 i = 0; i < objetMagique.length; i++){
            if(objetMagique[i] == _objetMagique) return true;
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

        require(objetMagiqueOwner[_tokenId] == _from);
        objetMagiqueOwner[_tokenId] = _to;
        ownerObjetMagiqueCount[_from]--;
        ownerObjetMagiqueCount[_to]++;
        
        emit Transfer( _from, _to, _tokenId);
    }
    
}










