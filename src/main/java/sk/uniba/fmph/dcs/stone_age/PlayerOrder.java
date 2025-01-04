
package sk.uniba.fmph.dcs.stone_age;

public final class PlayerOrder {
    private int order;
    private int players;

    public PlayerOrder(final int order, final int players) {
        this.order = order;
        this.players = players;
    }

    public int getOrder() {
        return order;
    }

    public int getPlayers() {
        return players;
    }

    public PlayerOrder forward() {
        int forward = (this.order + 1) % this.players;
        return new PlayerOrder(forward, this.players);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + order;
        result = prime * result + players;
        return result;
    }

    @Override
    public boolean equals(final Object obj) throws IllegalArgumentException {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PlayerOrder other = (PlayerOrder) obj;
        if (order != other.order) {
            return false;
        }
        if (players != other.players) {
            throw new AssertionError();
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlayerOrder [order=" + order + "]";
    }
}

