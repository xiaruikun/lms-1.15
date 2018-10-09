package com.next

class AssetPoolTeam
{
    String assetPoolName
    String username

    static belongsTo = [assetPool: AssetPool, user: User]

    static constraints = {
        assetPoolName maxSize: 32, nullable: true, blank: true
        username maxSize: 32, nullable: true, blank: true
        assetPool nullable: true, blank: true
        user nullable: true, blank: true
    }

    static mapping = {
        sort 'user'
    }

    def beforeInsert()
    {
        assetPool = AssetPool.findByName(assetPoolName)
        user = User.findByUsername(username)
    }

    def beforeUpdate()
    {
        assetPool = AssetPool.findByName(assetPoolName)
        user = User.findByUsername(username)
    }
}
