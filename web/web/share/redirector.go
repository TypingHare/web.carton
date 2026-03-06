package share

import "fmt"

func WebRedirector(args []string) ([]string, error) {
	if len(args) == 0 {
		return nil, fmt.Errorf("no name provided for redirection")
	}

	return []string{"open", args[0]}, nil
}
