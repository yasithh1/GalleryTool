import java.util.Scanner;
import java.util.Date;

class ImageNode
{
    String imageName;
    String type;
    Date date;
    ImageNode next;
    ImageNode prev;
    Boolean inRecyclebin;

    public ImageNode(String imageName,String type,Date date)
    {
        this.imageName = imageName;
        this.type = type;
        this.date = date;
        this.next = null;
        this.prev = null;
        this.inRecyclebin = false;
    }
}

class AlbumNode
{
    String album;
    AlbumNode prev;
    AlbumNode next;
    String albumName;
    ImageNode imageHead;
    boolean hide = false;


    public AlbumNode(String album)
    {
        this.album = album;
        this.prev = null;
        this.next = null;
        this.albumName=albumName;
        this.imageHead = null;
        this.hide = false;
    }

    //Add new Media file to album
    public void addImage(String imageName,String type,Date date)
    {
        ImageNode newImageNode = new ImageNode(imageName, type ,date);
        if(imageHead == null)
        {
            imageHead = newImageNode;
            //System.out.println(imageName + " Added");
        }
        else
        {
            ImageNode current = imageHead;
            while(current.next != null)
            {
                current = current.next;
            }
            current.next = newImageNode;
            newImageNode.prev = current;
            //System.out.println(imageName + " Added");
        }
    }


    //Recycle bin media delete
    public void deletePermanently(String imageName)
    {
        ImageNode current = imageHead;
        ImageNode previous = null;

        while (current != null)
        {
            if (current.imageName.equals(imageName) && current.inRecyclebin)
            {
                if (previous == null)
                {
                    imageHead = current.next;
                }
                else
                {
                    previous.next = current.next;
                }
                System.out.println(imageName + " deleted permanently.");
                return;
            }
            previous = current;
            current = current.next;
        }
        System.out.println(imageName + " not found");
    }

    //moving to  the recycle bin
    public void moveMedia(String searchMediaName,String type,Date date)
    {
        ImageNode moveMediaNode = new ImageNode(searchMediaName,type, date);

        if(imageHead == null)
        {
            imageHead = moveMediaNode;
            System.out.println(searchMediaName + " Added");
        }
        else
        {
            ImageNode current = imageHead;
            while(current.next != null)
            {
                current = current.next;
            }
            current.next = moveMediaNode;
            System.out.println(searchMediaName + " Added");
        }
    }

    //restore media
    public void restore(String imagename)
    {
        ImageNode current = imageHead;
        while(current != null)
        {
            if(current.imageName.equals(imagename) && current.inRecyclebin)
            {
                current.inRecyclebin = false;
                System.out.println("restored from recycle bin");

            }
            current = current.next;
        }
    }

    public void printRecycleMedia()
    {
        ImageNode current = imageHead;

        while(current !=null )
        {
            if(current.inRecyclebin)
            {
                System.out.println(current.imageName + "(" +current.date+")");

            }

            current = current.next;
        }
    }

    // Delete Media
    public void deleteMedia(String searchMediaName)
    {
        ImageNode current = imageHead;

        while(current != null)
        {
            // Find Selected Album
            if(current.imageName.equals(searchMediaName))
            {
                // Delete Head
                if(current == imageHead)
                {
                    imageHead = imageHead.next;

                    if (imageHead != null)
                    {
                        imageHead.prev = null;
                    }
                }

                // Delete last node
                else if (current.next == null)
                {
                    if (current.prev != null)
                    {  // This check ensures we're not at the head
                        current.prev.next = null;
                    }
                    else
                    {
                        imageHead = null;
                    }
                }

                //delete middle
                else
                {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                //System.out.println(albumName + " deleted");
                return;
            }
            else
            {
                current = current.next;
            }

        }

        System.out.println( searchMediaName+ " Can not find");
    }

    //Sorted by Date print Media
    public void printMediaDetails()
    {

        ImageNode current = imageHead;
        while (current != null)
        {
            System.out.println("Name: " + current.imageName + " | Type: " + current.type + " | Date: (" + current.date+")");
            current = current.next;
        }
    }

    //Search Album
    public ImageNode searchMedia(String searchMediaName)
    {
        ImageNode current = imageHead;
        while(current != null)
        {
            if(current.imageName.equals(searchMediaName))
            {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void printSelectTypeMedia(String mediaType)
    {
        int allMediaCount=0;
        int selectTypeCount=0;
        ImageNode current = imageHead;
        while(current != null)
        {
            allMediaCount++;
            if(current.type.equals(mediaType))
            {
                selectTypeCount++;
                System.out.println(current.imageName+"."+current.type);

            }
            current = current.next;
        }
        System.out.println("");
        System.out.println(selectTypeCount+" "+mediaType+" out of "+allMediaCount+" medias");
        System.out.println("");
    }


    // Print Media Details
    public void printMedidaDetails(String searchMediaName)
    {
        ImageNode current = imageHead;
        while(current != null)
        {
            if(current.imageName.equals(searchMediaName))
            {
                System.out.println("Date : "+current.date);
                System.out.println("Type : "+current.type);
                System.out.println();
            }
            current = current.next;
        }
    }

    void printMedia()
    {
        ImageNode current = imageHead;

        while(current != null)
        {
            System.out.println(current.imageName+ "." +current.type);
            current = current.next;
        }
    }

    //next media node
    public ImageNode getNextMedia(String currentMediaName)
    {
        ImageNode current = searchMedia(currentMediaName);
        if (current != null && current.next != null)
        {
            return current.next;
        }
        return null;
    }

    //Previous media node
    public ImageNode getPreviousMedia(String currentMediaName)
    {
        ImageNode current = searchMedia(currentMediaName);
        if (current != null && current.prev != null)
        {
            return current.prev;
        }
        return null;
    }




    // Bubble Sort
    public void sortMediaByDate() {
        if (imageHead == null || imageHead.next == null)
        {
            return;
        }

        boolean swapped;
        do {
            swapped = false;
            ImageNode current = imageHead;

            while (current.next != null)
            {
                if (current.date.compareTo(current.next.date) > 0)
                {

                    ImageNode temp = current.next;
                    current.next = temp.next;
                    temp.next = current;
                    if (current == imageHead)
                    {
                        imageHead = temp;
                    }
                    else
                    {
                        ImageNode prev = imageHead;
                        while (prev.next != current)
                        {
                            prev = prev.next;
                        }
                        prev.next = temp;
                    }
                    swapped = true;
                }
                else
                {
                    current = current.next;
                }
            }
        } while (swapped);
    }


}

class DoublyLinkedList
{
    AlbumNode head;


    public AlbumNode getAlbumNode(String albumName)
    {
        AlbumNode current = head;
        while (current != null)
        {
            if (current.album.equals(albumName))
            {
                return current;
            }
            current = current.next;
        }
        return null;
    }


    public DoublyLinkedList()
    {
        this.head = null;
    }

    public boolean updateAlbum(String albumName, String albumNewName)
    {
        AlbumNode current = head;

        while(current != null)
        {
            if(current.album.equals(albumName))
            {
                current.album = albumNewName;
                System.out.println(albumName + " Album Rename to " +albumNewName);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    //Copy albums list
    public void printSortedList()
    {
        // Create a temporary list and copy the original list to it
        DoublyLinkedList tempList = new DoublyLinkedList();
        AlbumNode current = head;

        // Copy nodes to the temporary list
        while (current != null)
        {
            tempList.insertAtEnd(current.album);
            current = current.next;
        }

        // Sort the temporary list
        tempList.sortAlbumsAlphabetically();

        // Print the sorted list
        AlbumNode sortedCurrent = tempList.head;
        System.out.println("Sorted Albums");
        while (sortedCurrent != null)
        {
            if (!sortedCurrent.hide)
            {
                System.out.print(sortedCurrent.album + "         ");
            }
            sortedCurrent = sortedCurrent.next;
        }
        System.out.println();
    }


    //Sorted by A-z Using Selection Sort
    public void sortAlbumsAlphabetically()
    {
        if (head == null || head.next == null)
        {
            return;
        }

        boolean swapped;
        do {
            swapped = false;
            AlbumNode current = head;

            while (current != null && current.next != null)
            {
                if (current.album.compareTo(current.next.album) > 0) {
                    // Swap the album names
                    String tempAlbum = current.album;
                    current.album = current.next.album;
                    current.next.album = tempAlbum;

                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }



    // Method to insert a node at the front of the linked list
    public void insertAtBeginning(String album)
    {
        AlbumNode newAlbumNode = new AlbumNode(album);
        newAlbumNode.next = head;

        if (head != null)
            head.prev = newAlbumNode;

        head = newAlbumNode;
    }

    //Search Album
    public boolean serchAlbum(String albumName)
    {
        AlbumNode current = head;
        while(current != null)
        {
            if(current.album.equals(albumName))
            {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Method to insert a node at the end of the  linked list
    public void insertAtEnd(String album)
    {
        AlbumNode newAlbumNode = new AlbumNode(album);
        AlbumNode last = head;

        newAlbumNode.next = null;

        if (head == null) {
            newAlbumNode.prev = null;
            head = newAlbumNode;
            return;
        }

        while (last.next != null)
            last = last.next;

        last.next = newAlbumNode;
        newAlbumNode.prev = last;
    }

    // Delete Method
    public void deleteAlbum(String albumName)
    {
        AlbumNode current = head;

        while(current != null)
        {
            // Find Selected Album
            if(current.album.equals(albumName))
            {
                // Delete Head
                if(current == head)
                {
                    head = head.next;

                    if (head != null)
                    {
                        head.prev = null;
                    }
                }

                // Delete last
                else if(current.next == null){
                    current.prev.next = null;

                }

                //delete middle
                else
                {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                //System.out.println(albumName + " deleted");
                return;
            }
            else
            {
                current = current.next;
            }

        }

        System.out.println(albumName + " Can not find");

    }

    //print Recyclebin medies
    public void printAllRecycleMedia()
    {
        int recycleCount = 0;
        AlbumNode current = head;
        while (current != null) {
            recycleCount++;
            current.printRecycleMedia();
            current = current.next;
        }
        System.out.println();
        System.out.println(" count :"+recycleCount);
        System.out.println();
    }

    //restore media in recyclebin
    public void restoreMedia(String mediaName)
    {
        AlbumNode current = head;

        while(current != null)
        {
            current.restore(mediaName);
            current = current.next;
        }
    }

    //delete media in recycle bin
    public void deleteMediaPermanently(String mediaName)
    {
        AlbumNode current = head;
        while (current != null) {
            current.deletePermanently(mediaName);
            current = current.next;
        }
    }

    // Display all albums
    public void printList()
    {
        int albumCount=0;
        AlbumNode current = head;

        System.out.println("Albums");
        while (current != null)
        {
            if(!current.hide)
            {
                albumCount++;
                System.out.print(current.album + "         ");
            }
            current = current.next;
        }
        System.out.println();
        System.out.println("Albums count :"+albumCount);
        System.out.println();
    }


    //View Media by Type
    public void viewMediaByType(String type)
    {

        int typeAlbumCount=0;

        boolean found = false;
        AlbumNode currentAlbumNode = head;
        while (currentAlbumNode != null)
        {
            ImageNode currentImageNode = currentAlbumNode.imageHead;
            boolean albumHasMedia = false;

            while (currentImageNode != null)
            {

                if (currentImageNode.type.equals(type))
                {

                    if(!albumHasMedia)
                    {
                        typeAlbumCount++;
                        System.out.println(currentAlbumNode.album+ " ");
                        //System.out.println("");
                        albumHasMedia = true;
                        found = true;
                    }
                }
                currentImageNode = currentImageNode.next;
            }
            currentAlbumNode = currentAlbumNode.next;
        }
        System.out.println("");
        System.out.println("Album count :"+typeAlbumCount);
        System.out.println("");
        if (!found)
        {
            System.out.println("No media found of type " + type);
        }
    }

    // Hide or Unhide Album
    public void hideUnhideAlbum(String album, String check)
    {
        AlbumNode current = head;

        while (current != null)
        {
            if (current.album.equals(album))
            {
                if (check.equals("hide"))
                {
                    current.hide = true;
                    System.out.println("Album "+album+" Hided.");
                }
                else if(check.equals("unhide"))
                {
                    current.hide = false;
                    System.out.print("Unhide the "+album+" album.");
                }
                return;
            }
            current = current.next;
        }
        System.out.println("Album " + album + " not found.");
    }

    public void showHideAlbums()
    {
        int count=0;
        AlbumNode current = head;

        System.out.println("Hide Albums ");
        System.out.println();
        while (current != null)
        {
            if(current.hide)
            {
                count++;
                System.out.print(current.album + "         ");
            }
            current = current.next;
        }
        System.out.println();
        System.out.println("Hide Albums :"+count);
        System.out.println();
    }

}

public class GalleryTool {
    public static void main(String []args) {
        Scanner scanner = new Scanner(System.in);
        DoublyLinkedList d1 = new DoublyLinkedList();

        d1.insertAtBeginning("Camera");
        d1.insertAtEnd("Favorite");
        d1.insertAtEnd("Recycle Bin");

        while(true) {
            System.out.println("1.Album");
            System.out.println("2.Video");
            System.out.println("3.Photo");
            System.out.println("4.Add Media");
            System.out.println("5.Recycle Bin");
            System.out.println("92.Exit");
            System.out.println();
            System.out.print("Option    : " );

            int number = scanner.nextInt();
            scanner.nextLine();
            System.out.println("------------------------------------------------------------");

            switch(number) {
                //Display Albums
                case 1:
                    boolean status = true;
                    while(status) {
                        d1.printList();
                        System.out.println();
                        System.out.println();
                        System.out.println();
                        System.out.println("1.Search Album");
                        System.out.println("2.Create Album");
                        System.out.println("3.View Hide Album");
                        System.out.println("4.Filter by A-z");
                        System.out.println("92.Back");
                        System.out.println();
                        System.out.print("Option  :  ");
                        int num = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("---------------------------------------------------------------------");

                        switch(num) {
                            case 1:
                                // Search and select album
                                System.out.print("Search Album Name:  ");
                                String albumName = scanner.nextLine();
                                System.out.println();
                                System.out.println();
                                System.out.println("---------------------------------------------------------------------");

                                if(d1.serchAlbum(albumName))
                                {

                                    AlbumNode foundAlbum = d1.getAlbumNode(albumName);
                                    boolean current = true;
                                    while(current) {
                                        // View Media
                                        System.out.println(albumName);
                                        System.out.println();
                                        foundAlbum.printMedia();
                                        System.out.println();
                                        System.out.println();
                                        System.out.println();
                                        System.out.println("1.Select Image");
                                        System.out.println("2.Rename Album");
                                        System.out.println("3.Delete Album");
                                        System.out.println("4.Hide the Album");
                                        System.out.println("5.Filter Media");
                                        System.out.println("92.Back");
                                        System.out.println();
                                        System.out.print("Option  :  ");
                                        int option = scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.println("---------------------------------------------------------------------");

                                        switch(option)
                                        {
                                            // Select a Media
                                            case 1:
                                                System.out.println(albumName);
                                                System.out.println();
                                                foundAlbum.printMedia();
                                                System.out.println();
                                                System.out.print("Search Media Name :  ");
                                                String searchMediaName = scanner.nextLine();
                                                System.out.println("---------------------------------------------------------------------");

                                                //Found media
                                                if(foundAlbum.searchMedia(searchMediaName) != null)
                                                {

                                                    boolean selectMediaStatus = true;
                                                    while(selectMediaStatus)
                                                    {
                                                        System.out.println(albumName);
                                                        System.out.println();
                                                        System.out.println(searchMediaName);
                                                        System.out.println();
                                                        foundAlbum.printMedidaDetails(searchMediaName);
                                                        System.out.println();
                                                        System.out.println("1.Delete Media ");
                                                        System.out.println("2.Add to Favorite ");
                                                        System.out.println("3.Move Media ");
                                                        System.out.println("4.Next Media ");
                                                        System.out.println("5.Previos Media ");
                                                        System.out.println("92.Back");
                                                        System.out.println();
                                                        System.out.print("Option  :  ");

                                                        int SelectMedia = scanner.nextInt();
                                                        scanner.nextLine();
                                                        System.out.println("------------------------------------------------------------");

                                                        switch(SelectMedia)
                                                        {
                                                            // Delete Media
                                                            case 1:
                                                                AlbumNode recycleAlbum = d1.getAlbumNode("Recycle Bin");

                                                                if (recycleAlbum != null)
                                                                {
                                                                    ImageNode foundImage = foundAlbum.searchMedia(searchMediaName);
                                                                    recycleAlbum.addImage(foundImage.imageName, foundImage.type, foundImage.date);
                                                                    foundAlbum.deleteMedia(searchMediaName);
                                                                    System.out.println(searchMediaName+ " Deleted");
                                                                    System.out.println();
                                                                    System.out.println("------------------------------------------------------------");
                                                                    selectMediaStatus = false;
                                                                    //System.out.println("Media added.");
                                                                }
                                                                else
                                                                {
                                                                    System.out.println("Recycle album not found.");
                                                                }
                                                                break;

                                                            //Media Add to Favorite
                                                            case 2:
                                                                AlbumNode favoriteAlbum = d1.getAlbumNode("Favorite");

                                                                if (favoriteAlbum != null)
                                                                {
                                                                    ImageNode foundImage = foundAlbum.searchMedia(searchMediaName);
                                                                    favoriteAlbum.addImage(foundImage.imageName, foundImage.type, foundImage.date);
                                                                    foundAlbum.deleteMedia(searchMediaName);
                                                                    System.out.println(searchMediaName+ " Media Add to Favorite");
                                                                    System.out.println();
                                                                    System.out.println("------------------------------------------------------------");
                                                                    selectMediaStatus = false;
                                                                }
                                                                else
                                                                {
                                                                    System.out.println("Recycle album not found.");
                                                                }
                                                                break;

                                                            //Media Move to other Album
                                                            case 3:

                                                                System.out.println("Target Album Name : ");
                                                                String targetAlbumName = scanner.nextLine();

                                                                AlbumNode targetAlbum = d1.getAlbumNode(targetAlbumName);

                                                                if (targetAlbum != null)
                                                                {
                                                                    ImageNode foundImage = foundAlbum.searchMedia(searchMediaName);
                                                                    targetAlbum.addImage(foundImage.imageName, foundImage.type, foundImage.date);
                                                                    foundAlbum.deleteMedia(searchMediaName);
                                                                    System.out.println();
                                                                    System.out.println(searchMediaName+ " Media Add to "+targetAlbumName);
                                                                    System.out.println();
                                                                    System.out.println("------------------------------------------------------------");
                                                                    selectMediaStatus = false;
                                                                }
                                                                else
                                                                {
                                                                    System.out.println(targetAlbumName+" album not found.");
                                                                }
                                                                break;

                                                            //Next media
                                                            case 4:
                                                                ImageNode nextImage = foundAlbum.getNextMedia(searchMediaName);
                                                                if (nextImage != null)
                                                                {
                                                                    searchMediaName = nextImage.imageName;
                                                                    System.out.println("Moved to Next Media: " + searchMediaName);
                                                                }
                                                                else
                                                                {
                                                                    System.out.println("This is the last media in the album.");
                                                                }

                                                                break;

                                                            //Prev media
                                                            case 5:

                                                                ImageNode prevImage = foundAlbum.getPreviousMedia(searchMediaName);

                                                                if (prevImage != null)
                                                                {
                                                                    searchMediaName = prevImage.imageName;
                                                                    System.out.println("Moved to Previous Media: " + searchMediaName);

                                                                }
                                                                else
                                                                {
                                                                    System.out.println("This is the first media in the album.");
                                                                }

                                                                break;

                                                            case 92:
                                                                selectMediaStatus = false;
                                                                break;
                                                        }
                                                    }
                                                }

                                                else
                                                {
                                                    System.out.println("Cannot Found " + searchMediaName + " Media !");
                                                }



                                                break;
                                            // Rename Album name
                                            case 2:
                                                System.out.println(albumName + " Album Rename");
                                                System.out.println();
                                                System.out.print("Enter New Name : "  );
                                                System.out.println();
                                                System.out.println();
                                                String albumNewName = scanner.nextLine();
                                                d1.updateAlbum(albumName, albumNewName);
                                                System.out.println();
                                                System.out.println();
                                                System.out.println();
                                                System.out.println("---------------------------------------------------------------------");
                                                break;

                                            // Delete Album
                                            case 3:
                                                System.out.println("Are You Sure Delete This Album..!");
                                                System.out.println("1.Yes");
                                                System.out.println("2.No");
                                                System.out.println();
                                                System.out.print("Option  :  ");
                                                int deletOption = scanner.nextInt();
                                                System.out.println("---------------------------------------------------------------------");

                                                boolean deleteStatus = true;
                                                while(deleteStatus) {
                                                    if(deletOption == 1) {
                                                        d1.deleteAlbum(albumName);
                                                        System.out.println(albumName +" Album Deleted");
                                                        System.out.println();
                                                        System.out.println();
                                                        System.out.println("---------------------------------------------------------------------");
                                                        deleteStatus = false;
                                                        current = false;
                                                    } else {
                                                        deleteStatus = false;
                                                    }
                                                }
                                                break;

                                            // Hide album
                                            case 4:
                                                d1.hideUnhideAlbum(albumName, "hide");
                                                System.out.println();
                                                System.out.println("---------------------------------------------------------------------");
                                                current = false;
                                                break;

                                            // Filter media
                                            case 5:

                                                foundAlbum.sortMediaByDate();
                                                foundAlbum.printMediaDetails();
                                                System.out.println();
                                                System.out.println("---------------------------------------------------------------------");
                                                current = false;
                                                break;
                                            // Exit out album
                                            case 92:
                                                current = false;
                                                break;
                                        }
                                    }
                                }
                                else
                                {
                                    System.out.println(albumName + " Can Not Found");
                                }
                                break;
                            case 2:
                                // Create new album
                                System.out.print("Enter Name :  ");
                                String name = scanner.nextLine();
                                d1.insertAtEnd(name);
                                System.out.println();
                                System.out.println();
                                break;

                            case 3:
                                // Hide Album View
                                System.out.println();
                                d1.showHideAlbums();
                                System.out.println();
                                System.out.println("---------------------------------------------------------------------");
                                System.out.println("1.Unhide Album");
                                System.out.println("92.Back");
                                System.out.println();
                                System.out.println();
                                System.out.print("Option  :  ");
                                int hideOption = scanner.nextInt();
                                System.out.println("---------------------------------------------------------------------");

                                scanner.nextLine();
                                boolean hideStatus = true;
                                //scanner.nextLine();
                                while(hideStatus)
                                {
                                    switch(hideOption)
                                    {
                                        case 1:
                                            System.out.print("Search Album Name:  ");
                                            String currentAlbumName = scanner.nextLine();

                                            d1.hideUnhideAlbum(currentAlbumName, "unhide");
                                            System.out.println("");
                                            System.out.println("---------------------------------------------------------------------");
                                            System.out.println("");
                                            hideStatus = false;
                                            break;

                                        case 92:
                                            // Go Back
                                            hideStatus = false;
                                            break;
                                    }
                                }
                                break;

                            case 4:

                                System.out.println("Albums sorted alphabetically.");
                                System.out.println();
                                d1.printSortedList();
                                System.out.println("---------------------------------------------------------------------");

                                break;

                            case 92:

                                status = false;
                                break;
                        }
                    }
                    break;

                // Video Category
                case 2:
                    boolean videoStatus = true;
                    while(videoStatus)
                    {
                        System.out.println("Videos");
                        System.out.println();
                        d1.viewMediaByType("mp4");
                        System.out.println();
                        System.out.println();
                        System.out.println("1.Search Album");
                        System.out.println("92.Back");
                        System.out.println();
                        System.out.println();
                        System.out.print("Option  :  ");
                        int videoOption = scanner.nextInt();
                        System.out.println("---------------------------------------------------------------------");

                        if(videoOption == 1)
                        {
                            // Search and select album
                            System.out.print("Search Album Name:  ");
                            scanner.nextLine();
                            String videoAlbumName = scanner.nextLine();
                            System.out.println();
                            System.out.println("---------------------------------------------------------------------");

                            if(d1.serchAlbum(videoAlbumName) == true)
                            {
                                AlbumNode foundAlbum = d1.getAlbumNode(videoAlbumName);

                                System.out.println(videoAlbumName);
                                System.out.println();
                                String mediaType = "mp4";
                                foundAlbum.printSelectTypeMedia(mediaType);
                                System.out.println();
                                System.out.println("92.Back");
                                System.out.println();
                                System.out.print("Option  :  ");
                                int back = scanner.nextInt();
                                System.out.println();
                                System.out.println("---------------------------------------------------------------------");
                            }
                            else
                            {
                                System.out.println(videoAlbumName+" Can not found...!");
                            }
                        }
                        else
                        {
                            //videoStatus = false;
                            break;
                        }
                    }
                    break;

                // Photo Category
                case 3:
                    boolean imageStatus = true;
                    while(imageStatus)
                    {
                        System.out.println("Images");
                        System.out.println();
                        d1.viewMediaByType("png");
                        System.out.println();
                        System.out.println();
                        System.out.println("1.Search Album");
                        System.out.println("92.Back");
                        System.out.println();
                        System.out.println();
                        System.out.print("Option  :  ");
                        int imageOption = scanner.nextInt();
                        System.out.println("---------------------------------------------------------------------");

                        if(imageOption == 1)
                        {
                            // Search and select album
                            System.out.print("Search Album Name:  ");
                            scanner.nextLine();
                            String imageAlbumName = scanner.nextLine();
                            System.out.println();
                            System.out.println("---------------------------------------------------------------------");

                            if(d1.serchAlbum(imageAlbumName) == true)
                            {
                                AlbumNode foundAlbum = d1.getAlbumNode(imageAlbumName);

                                System.out.println(imageAlbumName);
                                System.out.println();
                                String mediaType = "png";
                                foundAlbum.printSelectTypeMedia(mediaType);
                                System.out.println();
                                System.out.println("92.Back");
                                System.out.println();
                                System.out.print("Option  :  ");
                                int back = scanner.nextInt();
                                System.out.println();
                                System.out.println("---------------------------------------------------------------------");
                            }
                            else
                            {
                                System.out.println(imageAlbumName+" Can not found...!");
                            }
                        }
                        else
                        {
                            //videoStatus = false;
                            break;
                        }
                    }

                    break;
                case 4:
                    // Add new media to default album
                    System.out.print("Enter Media Name  :  ");
                    String mediaName = scanner.nextLine();
                    System.out.print("Enter File type (png / mp4): ");
                    String type = scanner.nextLine();
                    Date date = new Date();
                    System.out.println();
                    System.out.println();
                    AlbumNode cameraAlbum = d1.getAlbumNode("Camera");
                    if (cameraAlbum != null) {
                        cameraAlbum.addImage(mediaName, type, date);
                        System.out.println(mediaName+" Media added.");
                    } else {
                        System.out.println("Camera album not found.");
                    }
                    System.out.println();
                    System.out.println("------------------------------------------------------------");
                    break;
                case 5:
                    // Recycle Bin

                    boolean recycleBin = true;
                    while(recycleBin)
                    {
                        AlbumNode foundRecycleAlbum = d1.getAlbumNode("Recycle Bin");

                        System.out.println("Recycle Bin");
                        System.out.println();
                        foundRecycleAlbum.printMedia();
                        System.out.println();
                        System.out.println();
                        System.out.println("1.Restore Media ");
                        System.out.println("2.Delete Permanetly");
                        System.out.println("92.Back");

                        System.out.println();
                        System.out.print("Option  :  ");
                        int recycle = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("------------------------------------------------------------");

                        switch(recycle)
                        {
                            case 1:
                                // Restore Media

                                System.out.println("Enter media name: ");
                                String restoreName = scanner.nextLine();
                                AlbumNode restoreAlbum = d1.getAlbumNode("Camera");

                                if(restoreAlbum != null)
                                {
                                    ImageNode foundImage = foundRecycleAlbum.searchMedia(restoreName);
                                    restoreAlbum.addImage(foundImage.imageName, foundImage.type, foundImage.date);
                                    foundRecycleAlbum.deleteMedia(restoreName);
                                    System.out.println(restoreName+ " Media Add to Camera Album");
                                    System.out.println();
                                    System.out.println("------------------------------------------------------------");
                                }
                                else
                                {
                                    System.out.println("Camera album not found.");
                                }
                                recycleBin = false;
                                break;
                            case 2:
                                // Permanet Delete Media
                                System.out.println("Enter media name: ");
                                String deleteName = scanner.nextLine();
                                System.out.println();
                                System.out.println("Are You Sure Delete This Album..!");
                                System.out.println("1.Yes");
                                System.out.println("2.No");
                                System.out.println();
                                System.out.print("Option  :  ");
                                int deleteOption = scanner.nextInt();
                                System.out.println("---------------------------------------------------------------------");

                                if(deleteOption == 1)
                                {
                                    AlbumNode recycleAlbum = d1.getAlbumNode("Recycle Bin");
                                    if (recycleAlbum != null)
                                    {
                                        ImageNode foundImage = recycleAlbum.searchMedia(deleteName);
                                        recycleAlbum.addImage(foundImage.imageName, foundImage.type, foundImage.date);
                                        recycleAlbum.deleteMedia(deleteName);
                                        System.out.println(deleteName+ " Deleted");
                                        System.out.println();
                                        System.out.println("------------------------------------------------------------");
                                        recycleBin = false;
                                    }
                                }
                                else
                                {
                                    System.out.println("Recycle album not found.");
                                    recycleBin = false;
                                }

                                break;
                            case 92:
                                recycleBin = false;
                                break;
                        }
                    }
                    break;
                case 92:

                    System.out.println("Exiting the application.");
                    System.exit(0);
                    break;
            }
        }
    }
}