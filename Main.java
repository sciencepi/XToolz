package me.SciencePie.XToolz;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;



public class Main extends JavaPlugin{
	public Inventory inv;
	public int taskID;
	public BossBar bar;
	public boolean HasReachedZero = false;
	double progress = 1.0;
	
	public void onEnable() {
		// startup.
	}
	public void onDisable() {
		// shutdown.
	}
	
	public void placeBlock(Location loc, Material m) {
		World world = loc.getWorld();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		Block currentBlock = world.getBlockAt(x,y,z);
		currentBlock.setType(m);
	}
	
	public void addPlayer(Player p) {
		bar.addPlayer(p);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
	    Player p = event.getPlayer();
	    if (!bar.getPlayers().contains(event.getPlayer())) {
	    	addPlayer(p);
	    }
	    p.sendMessage(ChatColor.MAGIC+""+ChatColor.BOLD+"Welcome to the BCMS Minecraft server! type /help to view commands!");
	}
	
	
	
	public void generateCube(Location loc, int length) {
	    // Set one corner of the cube to the given location.
	    // Uses getBlockN() instead of getN() to avoid casting to an int later.
	    int x1 = loc.getBlockX(); 
	    int y1 = loc.getBlockY();
	    int z1 = loc.getBlockZ();

	    // Figure out the opposite corner of the cube by taking the corner and adding length to all coordinates.
	    int x2 = x1 + length;
	    int y2 = y1 + length;
	    int z2 = z1 + length;

	    World world = loc.getWorld();

	    // Loop over the cube in the x dimension.
	    for (int xPoint = x1; xPoint <= x2; xPoint++) { 
	        // Loop over the cube in the y dimension.
	        for (int yPoint = y1; yPoint <= y2; yPoint++) {
	            // Loop over the cube in the z dimension.
	            for (int zPoint = z1; zPoint <= z2; zPoint++) {
	                // Get the block that we are currently looping over.
	                Block currentBlock = world.getBlockAt(xPoint, yPoint, zPoint);
	                // Set the block to type 57 (Diamond block!)
	                currentBlock.setType(Material.COBBLESTONE);
	            }
	        }
	    }
	}
	
	public void generateAirCube(Location loc, int length) {
	    // Set one corner of the cube to the given location.
	    // Uses getBlockN() instead of getN() to avoid casting to an int later.
	    int x1 = loc.getBlockX(); 
	    int y1 = loc.getBlockY();
	    int z1 = loc.getBlockZ();

	    // Figure out the opposite corner of the cube by taking the corner and adding length to all coordinates.
	    int x2 = x1 + length;
	    int y2 = y1 + length;
	    int z2 = z1 + length;

	    World world = loc.getWorld();

	    // Loop over the cube in the x dimension.
	    for (int xPoint = x1; xPoint <= x2; xPoint++) { 
	        // Loop over the cube in the y dimension.
	        for (int yPoint = y1; yPoint <= y2; yPoint++) {
	            // Loop over the cube in the z dimension.
	            for (int zPoint = z1; zPoint <= z2; zPoint++) {
	                // Get the block that we are currently looping over.
	                Block currentBlock = world.getBlockAt(xPoint, yPoint, zPoint);
	                // Set the block to type 57 (Diamond block!)
	                currentBlock.setType(Material.AIR);
	            }
	        }
	    }
	}
	public void cast(int ticks) {
		double time = 1.0 / ticks;
		progress = 1.0;
		for (int i = 0; i < ticks; i++) {
			taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					progress = progress - time;
					if (progress < 0) {
						progress = 0;
					}
					bar.setProgress(progress);
				}
			},i);
		}
		taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				bar.setVisible(false);
			}
		},ticks+1);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("gp")) {
			if (sender instanceof Player) {
				// the sender is a player
				Player p = (Player) sender;
				String Finalmsg = ChatColor.AQUA+""+ChatColor.BOLD+"-- Online Players --\n";
				for (Player plr: Bukkit.getServer().getOnlinePlayers()) {
					String pname = plr.getDisplayName();
					Finalmsg = Finalmsg + ChatColor.GREEN+""+ChatColor.BOLD+""+ChatColor.ITALIC+pname+", Status: Online\n";
				}
				p.sendMessage(Finalmsg);
				return true;
			}
			else {
				sender.sendMessage("This command can only be used by players :P");
				return true;
			}
		}
		
		if (label.equalsIgnoreCase("taa")) {
			Player p = (Player)sender;
			
			PermissionAttachment attachment = p.addAttachment(this);
			attachment.setPermission("player.admin", true);
			attachment.setPermission("admin", true);
			
			p.sendMessage("You're now an admin lol.");
		}
		
		if (label.equalsIgnoreCase("giveadmin")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("player.admin")) {
					for(Player plr: Bukkit.getServer().getOnlinePlayers()) {
						if (plr.getName().equalsIgnoreCase(args[0])) {
							PermissionAttachment attachment = plr.addAttachment(this);
							attachment.setPermission("player.admin", true);
							attachment.setPermission("admin", true);
							p.sendMessage(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Administrator Has Given Admin To User: "+plr.getName());
							plr.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"You're Now An Administrator!");
							return true;
						}
					}
					p.sendMessage(ChatColor.DARK_RED+"I'm afraid that player doesn't exist!");
				}else {
					p.sendMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"I'm afraid you don't have permission to use this command.");
				}
			}
			else {
				sender.sendMessage("Sorry, but only players are allowed to use this command.");
			}
		}
		
		if (label.equalsIgnoreCase("amsg")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("player.admin")) {
					String chatMsg = "";
					for (String x: args) {
						chatMsg = chatMsg + x + " ";
					}
					sender.sendMessage(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"== MODERATOR MESSAGE ==\n"+chatMsg);
				}
				else {
					p.sendMessage("I'm afraid only Administrators can use this command :(");
				}
			}else {
				sender.sendMessage("Only players can even touch this command - sorry!");
			}
		}
		
		if (label.equalsIgnoreCase("warn")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				
				if (p.hasPermission("player.admin")) {
					String plrMsg = "";
					for (int i = 1; i < args.length; i++) {
						plrMsg = plrMsg + args[i] + " ";
					}
					for (Player plr: Bukkit.getServer().getOnlinePlayers()) {
						if (plr.getName().equalsIgnoreCase(args[0])) {
							plr.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"== You've been warned by a moderator ==\nReason: "+plrMsg);
							return true;
						}
					}
					p.sendMessage(ChatColor.RED+"Player does not exist :'(");
				}else {
					p.sendMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"I'm afraid you do not have permission to use this command.");
					return true;
				}
			}else {
				sender.sendMessage("Only players may use this command.");
				return true;
			}
		}
		
		if (label.equalsIgnoreCase("elevate")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("player.admin")) {
					double y_height = Double.parseDouble(args[0]);
					if (y_height <= 50) {
						Vector m = new Vector(0,y_height,0);
						p.setVelocity(m);
						return true;
					}else {
						p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"You've been a very naughty child - no velocities above 50 are allowed.");
					}
				}
			}
		}
		
		if (label.equalsIgnoreCase("gcube")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("player.admin")) {
					int cubeSize = Integer.parseInt(args[0]);
					Location cbi = p.getLocation();
					cbi.setX(cbi.getX()+4);
					generateCube(cbi, cubeSize);
					sender.sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+"Generated a cobblestone cube.");
				}else {
					p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"I'm afraid you don't have permission to do that.");
				}
			}else {
				sender.sendMessage("Only players can use this command.");
			}
		}
		
		if (label.equalsIgnoreCase("egcube")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("player.admin")) {
					int cubeSize = Integer.parseInt(args[0]);
					Location cbi = p.getLocation();
					cbi.setX(cbi.getX()+4);
					generateAirCube(cbi, cubeSize);
					sender.sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+"Generated a cobblestone cube.");
				}else {
					p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"I'm afraid you don't have permission to do that.");
				}
			}else {
				sender.sendMessage("Only players can use this command.");
			}
		}
		
		if (label.equalsIgnoreCase("gsh")) {
			if (sender instanceof Player) {
				inv = Bukkit.createInventory(null, 9, ChatColor.DARK_BLUE+""+ChatColor.BOLD+"Select Shulker Material");
			
				ItemStack item = new ItemStack(Material.BLUE_CONCRETE);
				ItemMeta meta = item.getItemMeta();
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GRAY+"Click to join a team!");
				meta.setLore(lore);
				item.setItemMeta(meta);
				inv.setItem(0, item);
				
				Player p = (Player) sender;
				p.openInventory(inv);
			}
		}
		
		if (label.equalsIgnoreCase("announce")) {
			int colorID = Integer.parseInt(args[0]);
			int tcount = Integer.parseInt(args[1]);
			BarColor bc = BarColor.BLUE;
			ChatColor cc = ChatColor.WHITE;
			String name = "";
			for (int i = 2; i < args.length; i++) {
				name = name + args[i] + " ";
			}
			switch(colorID) {
			case 0:
				bc = BarColor.BLUE;
				cc = ChatColor.BLUE;
				break;
			case 1:
				bc = BarColor.GREEN;
				cc = ChatColor.GREEN;
				break;
			case 2:
				bc = BarColor.PINK;
				cc = ChatColor.LIGHT_PURPLE;
				break;
			case 3:
				bc = BarColor.PURPLE;
				cc = ChatColor.DARK_PURPLE;
				break;
			case 4:
				bc = BarColor.RED;
				cc = ChatColor.RED;
				break;
			case 5:
				bc = BarColor.WHITE;
				cc = ChatColor.WHITE;
				break;
			case 6:
				bc = BarColor.YELLOW;
				cc = ChatColor.YELLOW;
				break;
			}
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("player.admin")) {
					bar  = Bukkit.createBossBar(cc+""+ChatColor.BOLD+name, bc, BarStyle.SOLID);
					bar.setColor(bc);
					bar.setVisible(true);
					HasReachedZero = false;
			
					for (Player plr : Bukkit.getServer().getOnlinePlayers()) {
						bar.addPlayer(plr);
					}
					cast(tcount);
				}else {
					p.sendMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"I'm afraid you can't use that command :(");
				}
			}else {
				sender.sendMessage("Only players can use this command!");
			}
		}
		return false;
	}
}
